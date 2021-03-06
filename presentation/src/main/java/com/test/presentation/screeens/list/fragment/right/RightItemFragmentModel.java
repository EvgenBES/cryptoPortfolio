package com.test.presentation.screeens.list.fragment.right;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.test.domain.entity.Coin;
import com.test.executor.LoadImagePicaso;
import com.test.presentation.base.recycler.BaseItemViewModel;
import com.test.presentation.base.recycler.ClickedItemModel;

import java.text.DecimalFormat;

import io.reactivex.subjects.PublishSubject;

public class RightItemFragmentModel extends BaseItemViewModel<Coin> {

    public ObservableField<String> price = new ObservableField<>("");
    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> symbol = new ObservableField<>("");
    public ObservableField<String> rank = new ObservableField<>("");
    public ObservableField<String> imageUrl = new ObservableField<>(" ");

    PublishSubject<ClickedItemModel<Coin>> buttonOneClickSubject;


    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        LoadImagePicaso.loaderAvatar(view, imageUrl);
    }


    //два вида формата для отображения цены
    private DecimalFormat formatDoubleZero = new DecimalFormat("##.00");
    private DecimalFormat formatQuadrupleZero = new DecimalFormat("#0.000");
    private Coin coin;
    private long id;

    //этот конструктор не обязательно, это для специфических кликах
    //например когда внутри item есть кнопки и нужно обрабатывать клики на них
    public RightItemFragmentModel(PublishSubject<ClickedItemModel<Coin>> buttonOneClickSubject) {
        this.buttonOneClickSubject = buttonOneClickSubject;
    }

    @Override
    public void setItem(Coin coin, int position) {
        this.coin = coin;
        this.id = coin.getId();
        if (coin.getPrice() > 100) {
            price.set(String.valueOf(formatDoubleZero.format(coin.getPrice())) + "$");
        } else {
            price.set(String.valueOf(formatQuadrupleZero.format(coin.getPrice())) + "$");
        }
        name.set(coin.getName());
        symbol.set(coin.getSymbol());
        rank.set(String.valueOf(coin.getRank()));
        imageUrl.set(String.format("https://s2.coinmarketcap.com/static/img/coins/64x64/%d.png", coin.getId()));
    }


    //этот метод не обязательно, это для специфических кликах
    //например когда внутри item есть кнопки и нужно обрабатывать клики на них
    public void onMyButtonOneClicked() {
        buttonOneClickSubject.onNext(new ClickedItemModel(coin, (int)id));
    }

}
