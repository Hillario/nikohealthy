package nikohealthy.com.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_registerspec{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("panel1").vw.setHeight((int)((100d / 100 * height)));
views.get("panel1").vw.setWidth((int)((100d / 100 * width)));
views.get("imageview1").vw.setLeft((int)((50d / 100 * width) - (views.get("imageview1").vw.getWidth() / 2)));
views.get("panel2").vw.setLeft((int)((50d / 100 * width) - (views.get("panel2").vw.getWidth() / 2)));
views.get("panel3").vw.setLeft((int)((50d / 100 * width) - (views.get("panel3").vw.getWidth() / 2)));
views.get("panel4").vw.setLeft((int)((50d / 100 * width) - (views.get("panel4").vw.getWidth() / 2)));
views.get("panel5").vw.setLeft((int)((50d / 100 * width) - (views.get("panel5").vw.getWidth() / 2)));
views.get("panel6").vw.setLeft((int)((50d / 100 * width) - (views.get("panel6").vw.getWidth() / 2)));
views.get("panel7").vw.setLeft((int)((50d / 100 * width) - (views.get("panel7").vw.getWidth() / 2)));
views.get("panel8").vw.setLeft((int)((50d / 100 * width) - (views.get("panel8").vw.getWidth() / 2)));
views.get("panel9").vw.setLeft((int)((50d / 100 * width) - (views.get("panel9").vw.getWidth() / 2)));
views.get("panel10").vw.setLeft((int)((50d / 100 * width) - (views.get("panel10").vw.getWidth() / 2)));
views.get("panel11").vw.setLeft((int)((50d / 100 * width) - (views.get("panel11").vw.getWidth() / 2)));
views.get("panel12").vw.setLeft((int)((50d / 100 * width) - (views.get("panel12").vw.getWidth() / 2)));
views.get("panel13").vw.setLeft((int)((50d / 100 * width) - (views.get("panel13").vw.getWidth() / 2)));
views.get("panel14").vw.setLeft((int)((50d / 100 * width) - (views.get("panel14").vw.getWidth() / 2)));
views.get("panel15").vw.setLeft((int)((50d / 100 * width) - (views.get("panel15").vw.getWidth() / 2)));
views.get("panel16").vw.setLeft((int)((50d / 100 * width) - (views.get("panel16").vw.getWidth() / 2)));
views.get("panel17").vw.setLeft((int)((50d / 100 * width) - (views.get("panel17").vw.getWidth() / 2)));
views.get("imageview2").vw.setLeft((int)((50d / 100 * width) - (views.get("imageview2").vw.getWidth() / 2)));
views.get("btnupload").vw.setLeft((int)((50d / 100 * width) - (views.get("btnupload").vw.getWidth() / 2)));
//BA.debugLineNum = 28;BA.debugLine="btnsignup.HorizontalCenter=50%x"[registerspec/General script]
views.get("btnsignup").vw.setLeft((int)((50d / 100 * width) - (views.get("btnsignup").vw.getWidth() / 2)));

}
}