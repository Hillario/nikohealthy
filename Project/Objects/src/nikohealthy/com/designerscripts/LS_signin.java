package nikohealthy.com.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_signin{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.5d);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3d);
views.get("panel2").vw.setWidth((int)((100d / 100 * width)));
views.get("btnback").vw.setLeft((int)((0d / 100 * width)));
views.get("label1").vw.setLeft((int)((50d / 100 * width) - (views.get("label1").vw.getWidth() / 2)));
views.get("label1").vw.setTop((int)((0d / 100 * height)));
views.get("btnmore").vw.setLeft((int)((100d / 100 * width) - (views.get("btnmore").vw.getWidth())));
views.get("scrollview1").vw.setHeight((int)((100d / 100 * height)));
views.get("scrollview1").vw.setWidth((int)((100d / 100 * width)));
views.get("scrollview1").vw.setTop((int)((views.get("panel2").vw.getTop() + views.get("panel2").vw.getHeight())));

}
}