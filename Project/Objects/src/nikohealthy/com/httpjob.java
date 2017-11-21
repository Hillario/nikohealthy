package nikohealthy.com;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class httpjob extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "nikohealthy.com.httpjob");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", nikohealthy.com.httpjob.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public String _vvvvv1 = "";
public boolean _vvvvv2 = false;
public String _vvvvv3 = "";
public String _vvvvv4 = "";
public String _vvvvv5 = "";
public Object _vvvvv6 = null;
public String _vvvvv7 = "";
public String _vvvvv0 = "";
public anywheresoftware.b4a.http.HttpClientWrapper.HttpUriRequestWrapper _vvvvvv1 = null;
public nikohealthy.com.main _vvvvvv2 = null;
public nikohealthy.com.httputils2service _vvvvvv3 = null;
public nikohealthy.com.global _vvvvvv4 = null;
public nikohealthy.com.signin _vvvvvv5 = null;
public nikohealthy.com.cancercare _vvvvvv6 = null;
public nikohealthy.com.login _vvvvvv7 = null;
public nikohealthy.com.loginselecto _vvvvvv0 = null;
public nikohealthy.com.contactsportal _vvvvvvv1 = null;
public nikohealthy.com.contactsportalusers _vvvvvvv2 = null;
public nikohealthy.com.signinspec _vvvvvvv3 = null;
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 4;BA.debugLine="Public JobName As String";
_vvvvv1 = "";
 //BA.debugLineNum = 5;BA.debugLine="Public Success As Boolean";
_vvvvv2 = false;
 //BA.debugLineNum = 6;BA.debugLine="Public Username, Password As String";
_vvvvv3 = "";
_vvvvv4 = "";
 //BA.debugLineNum = 7;BA.debugLine="Public ErrorMessage As String";
_vvvvv5 = "";
 //BA.debugLineNum = 8;BA.debugLine="Private target As Object";
_vvvvv6 = new Object();
 //BA.debugLineNum = 9;BA.debugLine="Private mLink As String";
_vvvvv7 = "";
 //BA.debugLineNum = 10;BA.debugLine="Private taskId As String";
_vvvvv0 = "";
 //BA.debugLineNum = 11;BA.debugLine="Private req As HttpRequest";
_vvvvvv1 = new anywheresoftware.b4a.http.HttpClientWrapper.HttpUriRequestWrapper();
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public String  _vvv5(int _id) throws Exception{
 //BA.debugLineNum = 90;BA.debugLine="Public Sub Complete (id As Int)";
 //BA.debugLineNum = 91;BA.debugLine="taskId = id";
_vvvvv0 = BA.NumberToString(_id);
 //BA.debugLineNum = 92;BA.debugLine="CallSubDelayed2(target, \"JobDone\", Me)";
__c.CallSubDelayed2(getActivityBA(),_vvvvv6,"JobDone",this);
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return "";
}
public String  _vvv6(String _link) throws Exception{
 //BA.debugLineNum = 58;BA.debugLine="Public Sub Download(Link As String)";
 //BA.debugLineNum = 59;BA.debugLine="mLink = Link";
_vvvvv7 = _link;
 //BA.debugLineNum = 60;BA.debugLine="req.InitializeGet(Link)";
_vvvvvv1.InitializeGet(_link);
 //BA.debugLineNum = 61;BA.debugLine="CallSubDelayed2(HttpUtils2Service, \"SubmitJob\", M";
__c.CallSubDelayed2(getActivityBA(),(Object)(_vvvvvv3.getObject()),"SubmitJob",this);
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return "";
}
public String  _vvv7(String _link,String[] _parameters) throws Exception{
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
anywheresoftware.b4a.objects.StringUtils _su = null;
int _i = 0;
 //BA.debugLineNum = 68;BA.debugLine="Public Sub Download2(Link As String, Parameters()";
 //BA.debugLineNum = 69;BA.debugLine="mLink = Link";
_vvvvv7 = _link;
 //BA.debugLineNum = 70;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 71;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 72;BA.debugLine="sb.Append(Link)";
_sb.Append(_link);
 //BA.debugLineNum = 73;BA.debugLine="If Parameters.Length > 0 Then sb.Append(\"?\")";
if (_parameters.length>0) { 
_sb.Append("?");};
 //BA.debugLineNum = 74;BA.debugLine="Dim su As StringUtils";
_su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 75;BA.debugLine="For i = 0 To Parameters.Length - 1 Step 2";
{
final int step7 = (int) (2);
final int limit7 = (int) (_parameters.length-1);
for (_i = (int) (0) ; (step7 > 0 && _i <= limit7) || (step7 < 0 && _i >= limit7); _i = ((int)(0 + _i + step7)) ) {
 //BA.debugLineNum = 76;BA.debugLine="If i > 0 Then sb.Append(\"&\")";
if (_i>0) { 
_sb.Append("&");};
 //BA.debugLineNum = 77;BA.debugLine="sb.Append(su.EncodeUrl(Parameters(i), \"UTF8\")).A";
_sb.Append(_su.EncodeUrl(_parameters[_i],"UTF8")).Append("=");
 //BA.debugLineNum = 78;BA.debugLine="sb.Append(su.EncodeUrl(Parameters(i + 1), \"UTF8\"";
_sb.Append(_su.EncodeUrl(_parameters[(int) (_i+1)],"UTF8"));
 }
};
 //BA.debugLineNum = 80;BA.debugLine="req.InitializeGet(sb.ToString)";
_vvvvvv1.InitializeGet(_sb.ToString());
 //BA.debugLineNum = 81;BA.debugLine="CallSubDelayed2(HttpUtils2Service, \"SubmitJob\", M";
__c.CallSubDelayed2(getActivityBA(),(Object)(_vvvvvv3.getObject()),"SubmitJob",this);
 //BA.debugLineNum = 82;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _vvv0() throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _b = null;
 //BA.debugLineNum = 116;BA.debugLine="Public Sub GetBitmap As Bitmap";
 //BA.debugLineNum = 117;BA.debugLine="Dim b As Bitmap";
_b = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 118;BA.debugLine="b = LoadBitmap(HttpUtils2Service.TempFolder, task";
_b = __c.LoadBitmap(_vvvvvv3._v5,_vvvvv0);
 //BA.debugLineNum = 119;BA.debugLine="Return b";
if (true) return _b;
 //BA.debugLineNum = 120;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.streams.File.InputStreamWrapper  _vvvv1() throws Exception{
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _in = null;
 //BA.debugLineNum = 122;BA.debugLine="Sub GetInputStream As InputStream";
 //BA.debugLineNum = 123;BA.debugLine="Dim In As InputStream";
_in = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();
 //BA.debugLineNum = 124;BA.debugLine="In = File.OpenInput(HttpUtils2Service.TempFolder,";
_in = __c.File.OpenInput(_vvvvvv3._v5,_vvvvv0);
 //BA.debugLineNum = 125;BA.debugLine="Return In";
if (true) return _in;
 //BA.debugLineNum = 126;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.http.HttpClientWrapper.HttpUriRequestWrapper  _vvvv2() throws Exception{
 //BA.debugLineNum = 85;BA.debugLine="Public Sub GetRequest As HttpRequest";
 //BA.debugLineNum = 86;BA.debugLine="Return req";
if (true) return _vvvvvv1;
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return null;
}
public String  _vvvv3() throws Exception{
 //BA.debugLineNum = 101;BA.debugLine="Public Sub GetString As String";
 //BA.debugLineNum = 102;BA.debugLine="Return GetString2(\"UTF8\")";
if (true) return _vvvv4("UTF8");
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return "";
}
public String  _vvvv4(String _encoding) throws Exception{
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _tr = null;
String _res = "";
 //BA.debugLineNum = 106;BA.debugLine="Public Sub GetString2(Encoding As String) As Strin";
 //BA.debugLineNum = 107;BA.debugLine="Dim tr As TextReader";
_tr = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();
 //BA.debugLineNum = 108;BA.debugLine="tr.Initialize2(File.OpenInput(HttpUtils2Service.T";
_tr.Initialize2((java.io.InputStream)(__c.File.OpenInput(_vvvvvv3._v5,_vvvvv0).getObject()),_encoding);
 //BA.debugLineNum = 109;BA.debugLine="Dim res As String";
_res = "";
 //BA.debugLineNum = 110;BA.debugLine="res = tr.ReadAll";
_res = _tr.ReadAll();
 //BA.debugLineNum = 111;BA.debugLine="tr.Close";
_tr.Close();
 //BA.debugLineNum = 112;BA.debugLine="Return res";
if (true) return _res;
 //BA.debugLineNum = 113;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,String _name,Object _targetmodule) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 17;BA.debugLine="Public Sub Initialize (Name As String, TargetModul";
 //BA.debugLineNum = 18;BA.debugLine="JobName = Name";
_vvvvv1 = _name;
 //BA.debugLineNum = 19;BA.debugLine="target = TargetModule";
_vvvvv6 = _targetmodule;
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public String  _vvvv5(String _link,byte[] _data) throws Exception{
 //BA.debugLineNum = 27;BA.debugLine="Public Sub PostBytes(Link As String, Data() As Byt";
 //BA.debugLineNum = 28;BA.debugLine="mLink = Link";
_vvvvv7 = _link;
 //BA.debugLineNum = 29;BA.debugLine="req.InitializePost2(Link, Data)";
_vvvvvv1.InitializePost2(_link,_data);
 //BA.debugLineNum = 30;BA.debugLine="CallSubDelayed2(HttpUtils2Service, \"SubmitJob\", M";
__c.CallSubDelayed2(getActivityBA(),(Object)(_vvvvvv3.getObject()),"SubmitJob",this);
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public String  _vvvv6(String _link,String _dir,String _filename) throws Exception{
int _length = 0;
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _in = null;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
 //BA.debugLineNum = 35;BA.debugLine="Public Sub PostFile(Link As String, Dir As String,";
 //BA.debugLineNum = 36;BA.debugLine="Dim length As Int";
_length = 0;
 //BA.debugLineNum = 37;BA.debugLine="If Dir = File.DirAssets Then";
if ((_dir).equals(__c.File.getDirAssets())) { 
 //BA.debugLineNum = 38;BA.debugLine="Log(\"Cannot send files from the assets folder.\")";
__c.Log("Cannot send files from the assets folder.");
 //BA.debugLineNum = 39;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 41;BA.debugLine="length = File.Size(Dir, FileName)";
_length = (int) (__c.File.Size(_dir,_filename));
 //BA.debugLineNum = 42;BA.debugLine="Dim In As InputStream";
_in = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();
 //BA.debugLineNum = 43;BA.debugLine="In = File.OpenInput(Dir, FileName)";
_in = __c.File.OpenInput(_dir,_filename);
 //BA.debugLineNum = 44;BA.debugLine="If length < 1000000 Then '1mb";
if (_length<1000000) { 
 //BA.debugLineNum = 47;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 48;BA.debugLine="out.InitializeToBytesArray(length)";
_out.InitializeToBytesArray(_length);
 //BA.debugLineNum = 49;BA.debugLine="File.Copy2(In, out)";
__c.File.Copy2((java.io.InputStream)(_in.getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 50;BA.debugLine="PostBytes(Link, out.ToBytesArray)";
_vvvv5(_link,_out.ToBytesArray());
 }else {
 //BA.debugLineNum = 52;BA.debugLine="req.InitializePost(Link, In, length)";
_vvvvvv1.InitializePost(_link,(java.io.InputStream)(_in.getObject()),_length);
 //BA.debugLineNum = 53;BA.debugLine="CallSubDelayed2(HttpUtils2Service, \"SubmitJob\",";
__c.CallSubDelayed2(getActivityBA(),(Object)(_vvvvvv3.getObject()),"SubmitJob",this);
 };
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return "";
}
public String  _vvvv7(String _link,String _text) throws Exception{
 //BA.debugLineNum = 22;BA.debugLine="Public Sub PostString(Link As String, Text As Stri";
 //BA.debugLineNum = 23;BA.debugLine="PostBytes(Link, Text.GetBytes(\"UTF8\"))";
_vvvv5(_link,_text.getBytes("UTF8"));
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public String  _vvvv0() throws Exception{
 //BA.debugLineNum = 96;BA.debugLine="Public Sub Release";
 //BA.debugLineNum = 97;BA.debugLine="File.Delete(HttpUtils2Service.TempFolder, taskId)";
__c.File.Delete(_vvvvvv3._v5,_vvvvv0);
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
