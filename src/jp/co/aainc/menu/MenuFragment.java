package jp.co.aainc.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Takahata
 * Date: 2013/05/16
 * Time: 13:55
 * To change this template use File | Settings | File Templates.
 */
public class MenuFragment extends Fragment {

    private List<ImageView> viewList;   //メニューとなるのImageViewを格納
    private boolean isOpenMenu = false;//メニューが開いているかどうか判定用
    private final int RADIUS = 100;     //メニューが開いたときの半径の長さ
    private final int DEGREE = 90;     //メニューが開く角度

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.path, container, false);

        //適当に各メニューにクリック処理追加
        ImageView menu1 = (ImageView) v.findViewById(R.id.menu1);
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "メニュー１押した", Toast.LENGTH_SHORT).show();
            }
        });
        ImageView menu2 = (ImageView) v.findViewById(R.id.menu2);
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "メニュー２押した", Toast.LENGTH_SHORT).show();
            }
        });
        ImageView menu3 = (ImageView) v.findViewById(R.id.menu3);
        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "メニュー３押した", Toast.LENGTH_SHORT).show();
            }
        });
        ImageView menu4 = (ImageView) v.findViewById(R.id.menu4);
        menu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "メニュー４押した", Toast.LENGTH_SHORT).show();
            }
        });
        ImageView menu5 = (ImageView) v.findViewById(R.id.menu5);
        menu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "メニュー５押した", Toast.LENGTH_SHORT).show();
            }
        });

        viewList = new ArrayList<ImageView>();
        viewList.add(menu1);
        viewList.add(menu2);
        viewList.add(menu3);
        viewList.add(menu4);
        viewList.add(menu5);

        //ベースとなるボタンを押したら、アニメーション開始する
        v.findViewById(R.id.menuBase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOpenMenu) {
                    openAnimation();
                } else {
                    closeAnimation();
                }
                isOpenMenu = !isOpenMenu;
            }
        });

        return v;
    }

    //全体の角度から１つのメニュー同士の間の角度を取得
    public float getDegree() {
        return DEGREE / (viewList.size() - 1);
    }

    //角度と半径からx軸方向にどれだけ移動するか取得
    public int getTranslateX(float degree) {
        return (int) (RADIUS * Math.cos(Math.toRadians(degree)));
    }

    //角度と半径からy軸方向にどれだけ移動するか取得
    public int getTranslateY(float degree) {
        return (int) (RADIUS * Math.sin(Math.toRadians(degree)));
    }

    //メニューをオープンするメソッド
    public void openAnimation() {
        for (int i = 0; i < viewList.size(); i++) {
            //アニメーションで移動する分だけマージンを取る
            setMargin(i);
            //メニューが開くアニメーションを設定。
            TranslateAnimation openAnimation = new TranslateAnimation(-getTranslateX(getDegree() * i), 0, getTranslateY(getDegree() * i), 0);
            openAnimation.setDuration(500);
            openAnimation.setStartOffset(100 * i);
            AnticipateOvershootInterpolator overshootInterpolator = new AnticipateOvershootInterpolator(2);
            openAnimation.setInterpolator(overshootInterpolator);
            viewList.get(i).startAnimation(openAnimation);
        }
    }

    //メニューをクローズするアニメーション
    public void closeAnimation() {
        for (int i = 0; i < viewList.size(); i++) {
            //マージンを元に戻す
            resetMargin(i);
            //メニューが閉じるアニメーションを設定する
            TranslateAnimation closeAnimation = new TranslateAnimation(getTranslateX(getDegree() * i), 0, -getTranslateY(getDegree() * i), 0);
            closeAnimation.setDuration(300);
            closeAnimation.setStartOffset(100 * i);
            viewList.get(i).startAnimation(closeAnimation);
        }
    }

    //アニメーション後の座標までマージンを取るメソッド
    public void setMargin(int index) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewList.get(index).getLayoutParams();
        params.bottomMargin = getTranslateY(getDegree() * index);
        params.leftMargin   =  getTranslateX(getDegree() * index);
        viewList.get(index).setLayoutParams(params);
        viewList.get(index).setVisibility(View.VISIBLE);
    }

    //マージンを元に戻すメソッド
    public void resetMargin(int index) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewList.get(index).getLayoutParams();
        params.bottomMargin = 0;
        params.leftMargin = 0;
        viewList.get(index).setLayoutParams(params);
    }

}
