package com.quandoo.androidtask.ui.tables;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.quandoo.androidtask.databinding.TableCellBinding;
import com.quandoo.androidtask.domain.model.Customer;
import com.quandoo.androidtask.domain.model.Table;
import com.quandoo.androidtask.ui.base.BaseViewHolder;
import com.quandoo.androidtask.utils.Logger;
import com.quandoo.androidtask.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TablesRvAdapter extends RecyclerView.Adapter<TablesRvAdapter.TableViewHolder> implements Logger {


    interface TableClickListener {
        void onTableItemClick(Table clickedTableNetEntity);
    }

    private  List<Table> tables;
    private  List<Customer> customers;
    private final TableClickListener clickLstnr;

    TablesRvAdapter(final @NonNull TableClickListener clickLstnr) {
        this.clickLstnr = clickLstnr;
    }

    void replaceModel(List<Table> tables,List<Customer> customers){
        this.tables = tables;
        this.customers=customers;
    }


    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new TableViewHolder(TableCellBinding.inflate(LayoutInflater.from(viewGroup.getContext()),
                viewGroup,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder viewHolder, int i) {
        Table table = tables.get(i);

        viewHolder.onBind(table);
    }


    public String findUserImage(String userFirstNameLastName) {
        for (Customer customer : customers) {
            String fullName = customer.getFirstName() + " " + customer.getLastName();
            if (fullName.equals(userFirstNameLastName)) {
                return customer.getImageUrl();
            }
        }
        return null;
    }

    private int getTableShapeImageResourceId(String tableShape) {
        switch (tableShape) {
            case "circle":
                return R.drawable.ic_circle;
            case "square":
                return R.drawable.ic_square;
            default:
                return R.drawable.ic_rectangle;
        }

    }

    @Override
    public int getItemCount() {
        return (tables != null && !tables.isEmpty()) ? tables.size() : 0;
    }


    public Table getItem(int position) {
        return tables == null ||  tables.isEmpty() ? null : tables.get(position);
    }

    public  class TableViewHolder extends BaseViewHolder<Table> {

        private final TableCellBinding binding;

        TableViewHolder(TableCellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void onBind(Table table) {


            binding.tableId.setText("" + table.getId());

            //TODO : Set name and color depending on reservation status
            if (table.getReservedBy() != null) {
                binding.reservingCustomerName.setText(table.getReservedBy());
                binding.reservingCustomerName.setTextColor(Color.RED);

                //load reserving user image
                Picasso.get().load(findUserImage(table.getReservedBy())).into(binding.avatarImageView);
                binding.avatarImageView.setVisibility(View.VISIBLE);

            } else {
                binding.reservingCustomerName.setText(R.string.free);
                binding.reservingCustomerName.setTextColor(Color.GREEN);
                binding.avatarImageView.setVisibility(View.INVISIBLE);
            }


            binding.tableImageView.setImageResource(getTableShapeImageResourceId(table.getShape()));
            binding.getRoot().setOnClickListener(v -> clickLstnr.onTableItemClick(table));

        }
    }
}