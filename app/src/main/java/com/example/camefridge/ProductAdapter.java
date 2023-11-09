package com.example.camefridge;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.CardDesignHold> {

    private Context mContext;
    private List<FavoriteProduct> productList;
    private Database db;

    public ProductAdapter(Context mContext, List<FavoriteProduct> productList, Database db) {
        this.mContext = mContext;
        this.productList = productList;
        this.db = db;
    }

    public class CardDesignHold extends RecyclerView.ViewHolder{
        private TextView productInfo;
        private ImageView imageViewNokta;

        public CardDesignHold(@NonNull View itemView) {
            super(itemView);
            productInfo = itemView.findViewById(R.id.productInfo);
            imageViewNokta = itemView.findViewById(R.id.imageViewNokta);
        }
    }


    @NonNull
    @Override
    public CardDesignHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_card_design,parent,false);
        return new CardDesignHold(view);
    }

    @Override
    public void onBindViewHolder(final  CardDesignHold holder, int position) {
        final FavoriteProduct fProduct = productList.get(position);
        holder.productInfo.setText(fProduct.getProduct_name()+", " +"Minimum piece: "+(String.valueOf(fProduct.getProduct_limit())));
        holder.imageViewNokta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mContext, holder.imageViewNokta);
                popupMenu.getMenuInflater().inflate(R.menu.poppup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.action_delete:
                                Snackbar.make(holder.imageViewNokta,"Do you want to delete your favorite product?", Snackbar.LENGTH_SHORT)
                                        .setAction("Yes", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                new FavoritesDao().deleteFavorite(db,fProduct.getProduct_id());
                                                productList = new FavoritesDao().allFavorites(db);
                                                notifyDataSetChanged();
                                            }
                                        }).show();

                                return true;
                            case R.id.action_update:

                                showAlert(fProduct);
                                return true;
                            default:
                                return false;
                        }


                    }
                });
                        popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public void showAlert(final FavoriteProduct fProduct){
        System.out.println("showAlert girişi yapıldı");
        LayoutInflater layout =LayoutInflater.from(mContext);
        View design = layout.inflate(R.layout.alert_design,null);

        final EditText productText = design.findViewById(R.id.productText);
        final EditText limitText = design.findViewById(R.id.limitText);

        productText.setText(fProduct.getProduct_name());
        limitText.setText(String.valueOf(fProduct.getProduct_limit()));

        AlertDialog.Builder pName = new AlertDialog.Builder(mContext);
        pName.setTitle("Update your Favorite");
        pName.setView(design);
        pName.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String productName = productText.getText().toString().trim();
                String limit = limitText.getText().toString().trim();
                new FavoritesDao().updateFavorite(db,fProduct.getProduct_id(), productName, Integer.parseInt(limit));
                productList = new FavoritesDao().allFavorites(db);
                notifyDataSetChanged();

            }
        });

        pName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        pName.create().show();

    }
}
