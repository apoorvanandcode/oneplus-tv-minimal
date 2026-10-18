package tv.minimal.launcher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.tv.TvContract;
import android.media.tv.TvInputInfo;
import android.media.tv.TvInputManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** Minimal home screen: a fixed grid of app, HDMI and Settings tiles. No services, no network. */
public class MainActivity extends Activity {

    private static final String[][] APPS = {
            {"com.netflix.ninja", "Netflix"},
            {"com.spotify.tv.android", "Spotify"},
            {"com.sonyliv", "Sony LIV"},
            {"com.google.android.youtube.tv", "YouTube"},
            {"com.amazon.amazonvideo.livingroom", "Prime Video"},
            {"com.android.vending", "Play Store"},
    };
    private static final int COLUMNS = 4;
    private static final int TILE_W_DP = 240;
    private static final int TILE_H_DP = 135; // 16:9, matches Android TV app banners
    private static final int TILE_MARGIN_DP = 12;
    private static final int SIDE_GUTTER_DP = 48; // Android TV overscan-safe margin
    private static final int BACKGROUND = Color.rgb(16, 16, 16);
    private static final int TILE = Color.rgb(38, 38, 38);
    private static final int FOCUS = Color.rgb(61, 139, 255); // visible on white banners and dark tiles alike

    private static final class Tile {
        final String label;
        final Drawable banner;
        final Intent intent;

        Tile(String label, Drawable banner, Intent intent) {
            this.label = label;
            this.banner = banner;
            this.intent = intent;
        }
    }

    private LinearLayout grid;
    private int lastFocus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout root = new FrameLayout(this);
        root.setBackgroundColor(BACKGROUND);
        grid = new LinearLayout(this);
        grid.setOrientation(LinearLayout.VERTICAL);
        root.addView(grid, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        setContentView(root);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Rebuilt on every return so newly installed apps and HDMI changes show up.
        buildGrid();
    }

    @Override
    public void onBackPressed() {
        // Home has nowhere to go back to.
    }

    private void buildGrid() {
        grid.removeAllViews();
        List<Tile> tiles = loadTiles();
        List<View> views = new ArrayList<>();
        LinearLayout row = null;
        for (int i = 0; i < tiles.size(); i++) {
            if (i % COLUMNS == 0) {
                row = new LinearLayout(this);
                grid.addView(row);
            }
            View v = makeTileView(tiles.get(i), i);
            row.addView(v);
            views.add(v);
        }
        if (!views.isEmpty()) {
            views.get(Math.min(lastFocus, views.size() - 1)).requestFocus();
        }
    }

    private List<Tile> loadTiles() {
        PackageManager pm = getPackageManager();
        List<Tile> tiles = new ArrayList<>();

        for (String[] app : APPS) {
            Intent launch = pm.getLeanbackLaunchIntentForPackage(app[0]);
            if (launch == null) {
                launch = pm.getLaunchIntentForPackage(app[0]);
            }
            Drawable banner = null;
            if (launch != null) {
                banner = loadBanner(pm, launch, app[0]);
            } else {
                // Not installed: the tile opens its Play Store page instead.
                launch = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + app[0]));
            }
            tiles.add(new Tile(app[1], banner, launch));
        }

        TvInputManager tvInputs = (TvInputManager) getSystemService(TV_INPUT_SERVICE);
        if (tvInputs != null) {
            List<TvInputInfo> hdmi = new ArrayList<>();
            for (TvInputInfo info : tvInputs.getTvInputList()) {
                // Child inputs are HDMI-CEC devices behind a port; one tile per physical port.
                if (info.getType() == TvInputInfo.TYPE_HDMI && info.getParentId() == null) {
                    hdmi.add(info);
                }
            }
            Collections.sort(hdmi, new Comparator<TvInputInfo>() {
                @Override
                public int compare(TvInputInfo a, TvInputInfo b) {
                    return a.getId().compareTo(b.getId());
                }
            });
            for (int i = 0; i < hdmi.size(); i++) {
                Intent view = new Intent(Intent.ACTION_VIEW,
                        TvContract.buildChannelUriForPassthroughInput(hdmi.get(i).getId()));
                tiles.add(new Tile("HDMI " + (i + 1), null, view));
            }
        }

        tiles.add(new Tile("Settings", null, new Intent(Settings.ACTION_SETTINGS)));
        return tiles;
    }

    private static Drawable loadBanner(PackageManager pm, Intent launch, String pkg) {
        try {
            Drawable banner = pm.getActivityBanner(launch);
            return banner != null ? banner : pm.getApplicationBanner(pkg);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    private View makeTileView(final Tile tile, final int index) {
        FrameLayout v = new FrameLayout(this);
        v.setFocusable(true);
        v.setClickable(true);

        GradientDrawable bg = new GradientDrawable();
        bg.setColor(TILE);
        bg.setCornerRadius(dp(8));
        v.setBackground(bg);
        v.setClipToOutline(true);

        final GradientDrawable ring = new GradientDrawable();
        ring.setColor(Color.TRANSPARENT);
        ring.setCornerRadius(dp(8));
        v.setForeground(ring);

        if (tile.banner != null) {
            ImageView image = new ImageView(this);
            image.setImageDrawable(tile.banner);
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            v.addView(image, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            TextView text = new TextView(this);
            text.setText(tile.label);
            text.setTextColor(Color.WHITE);
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            text.setGravity(Gravity.CENTER);
            v.addView(text, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        v.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                ring.setStroke(hasFocus ? dp(4) : 0, FOCUS);
                float scale = hasFocus ? 1.08f : 1f;
                view.animate().scaleX(scale).scaleY(scale).setDuration(120).start();
                if (hasFocus) {
                    lastFocus = index;
                }
            }
        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launch(tile);
            }
        });

        // Fit COLUMNS tiles plus margins and a side gutter into the screen width, capped at the design size.
        int screenW = getResources().getDisplayMetrics().widthPixels;
        int tileW = Math.min(dp(TILE_W_DP), (screenW - 2 * dp(SIDE_GUTTER_DP)) / COLUMNS - 2 * dp(TILE_MARGIN_DP));
        int tileH = tileW * TILE_H_DP / TILE_W_DP;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(tileW, tileH);
        int m = dp(TILE_MARGIN_DP);
        lp.setMargins(m, m, m, m);
        v.setLayoutParams(lp);
        return v;
    }

    private void launch(Tile tile) {
        Intent intent = new Intent(tile.intent).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
        } catch (RuntimeException e) {
            Toast.makeText(this, "Could not open " + tile.label, Toast.LENGTH_SHORT).show();
        }
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
