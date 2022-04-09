package afkt.project.ui.widget.render;

import android.content.Context;
import android.util.SparseArray;

import java.lang.reflect.Constructor;

import afkt.project.ui.widget.render.circle.jump.DanceLoadingRenderer;
import afkt.project.ui.widget.render.scenery.ElectricFanLoadingRenderer;
import afkt.project.ui.widget.render.shapechange.CircleBroodLoadingRenderer;
import afkt.project.ui.widget.render.shapechange.CoolWaitLoadingRenderer;

public final class LoadingRendererFactory {
    private static final SparseArray<Class<? extends LoadingRenderer>> LOADING_RENDERERS = new SparseArray<>();

    static {

        //circle jump
        LOADING_RENDERERS.put(6, DanceLoadingRenderer.class);

        //scenery
        LOADING_RENDERERS.put(9, ElectricFanLoadingRenderer.class);

        //shape change
        LOADING_RENDERERS.put(14, CircleBroodLoadingRenderer.class);
        LOADING_RENDERERS.put(15, CoolWaitLoadingRenderer.class);
    }

    private LoadingRendererFactory() {
    }

    public static LoadingRenderer createLoadingRenderer(
            Context context,
            int loadingRendererId
    )
            throws Exception {
        Class<?>         loadingRendererClazz = LOADING_RENDERERS.get(loadingRendererId);
        Constructor<?>[] constructors         = loadingRendererClazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes != null
                    && parameterTypes.length == 1
                    && parameterTypes[0].equals(Context.class)) {
                constructor.setAccessible(true);
                return (LoadingRenderer) constructor.newInstance(context);
            }
        }

        throw new InstantiationException();
    }
}