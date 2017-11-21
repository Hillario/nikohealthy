package nikohealthy.com.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_homepanels{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("panel1").vw.setHeight((int)((100d / 100 * height)));
views.get("panel1").vw.setWidth((int)((100d / 100 * width)));
views.get("panel2").vw.setWidth((int)((100d / 100 * width)));
views.get("panel3").vw.setWidth((int)((100d / 100 * width)));
views.get("panel4").vw.setWidth((int)((100d / 100 * width)));
views.get("panel5").vw.setWidth((int)((100d / 100 * width)));
views.get("panel6").vw.setWidth((int)((100d / 100 * width)));
views.get("panel7").vw.setWidth((int)((100d / 100 * width)));
views.get("panel8").vw.setWidth((int)((100d / 100 * width)));
views.get("panel9").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 16;BA.debugLine="Panel10.Width=100%x"[homepanels/General script]
views.get("panel10").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 17;BA.debugLine="Panel11.Width=100%x"[homepanels/General script]
views.get("panel11").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 18;BA.debugLine="Panel12.Width=100%x"[homepanels/General script]
views.get("panel12").vw.setWidth((int)((100d / 100 * width)));

}
}