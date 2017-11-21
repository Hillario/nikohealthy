package nikohealthy.com.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_cancercare{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleRate(0.5)"[cancercare/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.5d);
//BA.debugLineNum = 3;BA.debugLine="AutoScaleAll"[cancercare/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 4;BA.debugLine="AutoScaleRate(0.3)"[cancercare/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3d);
//BA.debugLineNum = 7;BA.debugLine="btnBack.Left=0%x"[cancercare/General script]
views.get("btnback").vw.setLeft((int)((0d / 100 * width)));
//BA.debugLineNum = 8;BA.debugLine="Label1.HorizontalCenter=50%x"[cancercare/General script]
views.get("label1").vw.setLeft((int)((50d / 100 * width) - (views.get("label1").vw.getWidth() / 2)));
//BA.debugLineNum = 9;BA.debugLine="btnmore.Right=100%x"[cancercare/General script]
views.get("btnmore").vw.setLeft((int)((100d / 100 * width) - (views.get("btnmore").vw.getWidth())));
//BA.debugLineNum = 11;BA.debugLine="ScrollView1.Top=Panel2.Bottom"[cancercare/General script]
views.get("scrollview1").vw.setTop((int)((views.get("panel2").vw.getTop() + views.get("panel2").vw.getHeight())));
//BA.debugLineNum = 13;BA.debugLine="ScrollView1.Width=100%x"[cancercare/General script]
views.get("scrollview1").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 14;BA.debugLine="ScrollView1.Height=80%y"[cancercare/General script]
views.get("scrollview1").vw.setHeight((int)((80d / 100 * height)));
//BA.debugLineNum = 16;BA.debugLine="Panel1.Top=ScrollView1.Bottom+10dip"[cancercare/General script]
views.get("panel1").vw.setTop((int)((views.get("scrollview1").vw.getTop() + views.get("scrollview1").vw.getHeight())+(10d * scale)));
//BA.debugLineNum = 17;BA.debugLine="Panel1.Left=0%x"[cancercare/General script]
views.get("panel1").vw.setLeft((int)((0d / 100 * width)));
//BA.debugLineNum = 18;BA.debugLine="Panel1.Right=95%x"[cancercare/General script]
views.get("panel1").vw.setLeft((int)((95d / 100 * width) - (views.get("panel1").vw.getWidth())));

}
}