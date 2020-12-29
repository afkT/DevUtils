package afkt.project.ui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;

public class AutoGridView
        extends GridView {

    private static final String TAG        = AutoGridView.class.getSimpleName();
    private              int    numColumnsID;
    private              int    previousFirstVisible;
    private              int    numColumns = 1;

    public AutoGridView(
            Context context,
            AttributeSet attrs,
            int defStyle
    ) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public AutoGridView(
            Context context,
            AttributeSet attrs
    ) {
        super(context, attrs);
        init(attrs);
    }

    public AutoGridView(Context context) {
        super(context);
    }

    /**
     * Sets the numColumns based on the attributeset
     */
    private void init(AttributeSet attrs) {
        // Read numColumns out of the AttributeSet
        int count = attrs.getAttributeCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                String name = attrs.getAttributeName(i);
                if (name != null && name.equals("numColumns")) {
                    // Update columns
                    this.numColumnsID = attrs.getAttributeResourceValue(i, 1);
                    updateColumns();
                    break;
                }
            }
        }
        Log.d(TAG, "numColumns set to: " + numColumns);
    }

    /**
     * Reads the amount of columns from the resource file and
     * updates the "numColumns" variable
     */
    private void updateColumns() {
        this.numColumns = getContext().getResources().getInteger(numColumnsID);
    }

    @Override
    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
        super.setNumColumns(numColumns);

        Log.d(TAG, "setSelection --> " + previousFirstVisible);
        setSelection(previousFirstVisible);
    }

    @Override
    protected void onLayout(
            boolean changed,
            int leftPos,
            int topPos,
            int rightPos,
            int bottomPos
    ) {
        super.onLayout(changed, leftPos, topPos, rightPos, bottomPos);
        setHeights();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        updateColumns();
        setNumColumns(this.numColumns);
    }

    @Override
    protected void onScrollChanged(
            int newHorizontal,
            int newVertical,
            int oldHorizontal,
            int oldVertical
    ) {
        // Check if the first visible position has changed due to this scroll
        int firstVisible = getFirstVisiblePosition();
        if (previousFirstVisible != firstVisible) {
            // Update position, and update heights
            previousFirstVisible = firstVisible;
            setHeights();
        }

        super.onScrollChanged(newHorizontal, newVertical, oldHorizontal, oldVertical);
    }

    private void setHeights() {
        ListAdapter adapter = getAdapter();

        if (adapter != null) {
            for (int i = 0; i < getChildCount(); i += numColumns) {
                // Determine the maximum height for this row
                int maxHeight = 0;
                for (int j = i; j < i + numColumns; j++) {
                    View view = getChildAt(j);
                    if (view != null && view.getHeight() > maxHeight) {
                        maxHeight = view.getHeight();
                    }
                }
                //Log.d(TAG, "Max height for row #" + i/numColumns + ": " + maxHeight);

                // Set max height for each element in this row
                if (maxHeight > 0) {
                    for (int j = i; j < i + numColumns; j++) {
                        View view = getChildAt(j);
                        if (view != null && view.getHeight() != maxHeight) {
                            view.setMinimumHeight(maxHeight);
                        }
                    }
                }
            }
        }
    }
}