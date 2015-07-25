package com.jigdraw.draw.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.jigdraw.draw.R;
import com.jigdraw.draw.model.LongParceable;
import com.jigdraw.draw.tasks.JigsawLoader;
import com.jigdraw.draw.views.JigsawGridView;

/**
 * Represents the jigsaw puzzle solving activity.  After a user creates a
 * drawing then selects the difficulty of the jigsaw and this activity starts.
 *
 * @author Jay Paulynice
 */
public class JigsawActivity extends BaseJigsawActivity {
    /** Class name for logging */
    private static final String TAG = "JigsawActivity";

    /** The original image id to look up for jigsaw */
    private static final String ORIGINAL_IMG_ID = "originalId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting jigsaw activity...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jigsaw);
        initMenuBar();
        initGridView();
    }

    private void initGridView() {
        Log.d(TAG, "initializing jigsaw grid view");
        final JigsawGridView gridView = (JigsawGridView) findViewById(R.id
                .jigsaw_grid);

        JigsawLoader task = new JigsawLoader(getApplicationContext(), gridView);
        LongParceable longParceable = getIntent().getExtras().getParcelable(
                ORIGINAL_IMG_ID);
        task.execute(longParceable.getData());

        gridView.setOnItemLongClickListener(getOnItemLongClickListener
                (gridView));
        gridView.setOnDropListener(getOnDropListener(gridView));
        gridView.setOnDragListener(getOnDragListener());
    }

    private JigsawGridView.OnItemLongClickListener getOnItemLongClickListener(
            final JigsawGridView gridView) {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                gridView.startEditMode(position);
                return true;
            }
        };
    }

    private JigsawGridView.OnDropListener getOnDropListener(
            final JigsawGridView gridView) {
        return new JigsawGridView.OnDropListener() {
            @Override
            public void onActionDrop() {
                Log.d(TAG, "dropped element");
                gridView.stopEditMode();
            }
        };
    }

    private JigsawGridView.OnDragListener getOnDragListener() {
        return new JigsawGridView.OnDragListener() {
            @Override
            public void onDragStarted(int position) {
                Log.d(TAG, "dragging starts...position: " + position);
            }

            @Override
            public void onDragPositionsChanged(int oldPosition,
                                               int newPosition) {
                Log.d(TAG, String.format("drag changed from %d to %d",
                        oldPosition, newPosition));
            }
        };
    }
}