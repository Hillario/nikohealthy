package nikohealthy.com.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_loginselect{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("panel1").vw.setHeight((int)((100d / 100 * height)));
views.get("panel1").vw.setWidth((int)((100d / 100 * width)));
views.get("imageview2").vw.setLeft((int)((50d / 100 * width) - (views.get("imageview2").vw.getWidth() / 2)));
views.get("btnuser").vw.setLeft((int)((50d / 100 * width) - (views.get("btnuser").vw.getWidth() / 2)));
views.get("label2").vw.setLeft((int)((50d / 100 * width) - (views.get("label2").vw.getWidth() / 2)));
views.get("panel16").vw.setLeft((int)((50d / 100 * width) - (views.get("panel16").vw.getWidth() / 2)));
views.get("label3").vw.setLeft((int)((50d / 100 * width) - (views.get("label3").vw.getWidth() / 2)));

}
}