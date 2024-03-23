package com.example.lispelDoc2.uiServices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lispelDoc2.R;
import com.example.lispelDoc2.dao.ClientDAO;
import com.example.lispelDoc2.dao.ComponentDAO;
import com.example.lispelDoc2.dao.PrintUnitDAO;
import com.example.lispelDoc2.dao.ServiceDAO;
import com.example.lispelDoc2.database.LispelRoomDatabase;
import com.example.lispelDoc2.models.Order;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private final List<Order> orders;

    public OrderAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.order_item_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.order = orders.get(position);
        holder.context = context;

        ClientDAO clientDAO = LispelRoomDatabase.getDatabase(context).clientDAO();
        clientDAO.getEntityById(holder.order.getClientId()).observe((LifecycleOwner) context, gotClient -> {
            holder.clientTextView.setText(gotClient.getName());
        });

        holder.serviceDAO = LispelRoomDatabase.getDatabase(context).serviceDAO();
        holder.componentDAO = LispelRoomDatabase.getDatabase(context).componentDAO();
        holder.printUnitDAO = LispelRoomDatabase.getDatabase(context).printUnitDAO();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy  HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+10"));
        holder.dateTextView.setText(simpleDateFormat.format(holder.order.getDateOfCreate()));
        holder.priceTextView.setText(holder.order.getFinalPrice() + " руб");
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView dateTextView;
        final TextView servicesTextView;
        final TextView clientTextView;
        final TextView priceTextView;
        final ConstraintLayout mainLayout;
        private Order order;
        private ServiceDAO serviceDAO;
        private ComponentDAO componentDAO;
        private PrintUnitDAO printUnitDAO;
        private Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.main_layout);
            dateTextView = itemView.findViewById(R.id.date_TextView);
            clientTextView = itemView.findViewById(R.id.client_TextView);
            servicesTextView = itemView.findViewById(R.id.services_TextView);
            priceTextView = itemView.findViewById(R.id.price_TextView);
            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (servicesTextView.getVisibility() == View.GONE) {
                        servicesTextView.setVisibility(View.VISIBLE);

                        servicesTextView.setText("");
                        for (Long id : order.getServicesIdList()) {
                            serviceDAO.getEntityById(id).observe((LifecycleOwner) context, service -> {
                                printUnitDAO.getEntityByStickerNumber(service.getOrderUnitSticker()).observe((LifecycleOwner) context, gotPrintUnit -> {

                                    servicesTextView.setText(servicesTextView.getText() + service.getId().toString()+ " " + service.getOrderUnitSticker() + "\n" +  service.getName() + " " + gotPrintUnit.getPartName() + "а "
                                            + gotPrintUnit.getVendor() + " " + gotPrintUnit.getModel() + " " + gotPrintUnit.getOriginality()
                                             +  "\n" + "\n");

                                });

                            });
                        }
                    } else {
                        servicesTextView.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}
