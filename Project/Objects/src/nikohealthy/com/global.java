package nikohealthy.com;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class global {
private static global mostCurrent = new global();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static String _v6 = "";
public nikohealthy.com.main _vvvvvv2 = null;
public nikohealthy.com.httputils2service _vvvvvv3 = null;
public nikohealthy.com.signin _vvvvvv5 = null;
public nikohealthy.com.cancercare _vvvvvv6 = null;
public nikohealthy.com.login _vvvvvv7 = null;
public nikohealthy.com.loginselecto _vvvvvv0 = null;
public nikohealthy.com.contactsportal _vvvvvvv1 = null;
public nikohealthy.com.contactsportalusers _vvvvvvv2 = null;
public nikohealthy.com.signinspec _vvvvvvv3 = null;
public static String  _v7(anywheresoftware.b4a.BA _ba) throws Exception{
long _today = 0L;
int _year = 0;
int _month = 0;
int _day = 0;
int _hour = 0;
int _minute = 0;
int _second = 0;
String _timestamp = "";
 //BA.debugLineNum = 10;BA.debugLine="Sub currentDateTime As String";
 //BA.debugLineNum = 12;BA.debugLine="Dim today As Long";
_today = 0L;
 //BA.debugLineNum = 13;BA.debugLine="Dim year As Int";
_year = 0;
 //BA.debugLineNum = 14;BA.debugLine="Dim month As Int";
_month = 0;
 //BA.debugLineNum = 15;BA.debugLine="Dim day As Int";
_day = 0;
 //BA.debugLineNum = 16;BA.debugLine="Dim hour As Int";
_hour = 0;
 //BA.debugLineNum = 17;BA.debugLine="Dim minute As Int";
_minute = 0;
 //BA.debugLineNum = 18;BA.debugLine="Dim second As Int";
_second = 0;
 //BA.debugLineNum = 20;BA.debugLine="today=DateTime.Now";
_today = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 22;BA.debugLine="year=DateTime.GetYear(today)";
_year = anywheresoftware.b4a.keywords.Common.DateTime.GetYear(_today);
 //BA.debugLineNum = 23;BA.debugLine="month=DateTime.GetMonth(today)";
_month = anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(_today);
 //BA.debugLineNum = 24;BA.debugLine="day=DateTime.GetDayOfMonth(today)";
_day = anywheresoftware.b4a.keywords.Common.DateTime.GetDayOfMonth(_today);
 //BA.debugLineNum = 25;BA.debugLine="hour=DateTime.GetHour(today)";
_hour = anywheresoftware.b4a.keywords.Common.DateTime.GetHour(_today);
 //BA.debugLineNum = 26;BA.debugLine="minute=DateTime.GetMinute(today)";
_minute = anywheresoftware.b4a.keywords.Common.DateTime.GetMinute(_today);
 //BA.debugLineNum = 27;BA.debugLine="second=DateTime.GetSecond(today)";
_second = anywheresoftware.b4a.keywords.Common.DateTime.GetSecond(_today);
 //BA.debugLineNum = 29;BA.debugLine="Dim timestamp As String";
_timestamp = "";
 //BA.debugLineNum = 31;BA.debugLine="timestamp=year&\"-\"&month&\"-\"&day&\" \"&hour&\":\"&min";
_timestamp = BA.NumberToString(_year)+"-"+BA.NumberToString(_month)+"-"+BA.NumberToString(_day)+" "+BA.NumberToString(_hour)+":"+BA.NumberToString(_minute)+":"+BA.NumberToString(_second);
 //BA.debugLineNum = 32;BA.debugLine="Return timestamp";
if (true) return _timestamp;
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Dim API As String=\"http://cardiaccenter.co.ke/nik";
_v6 = BA.__b (new byte[] {109,111,40,127,53,35,119,119,112,112,40,36,105,126,57,107,96,118,51,98,46,104,54,57,104,110,54,109,97,111,56,121,107,100,49,114,98,103,108,102,117,43,53,112,97,106,114,120,108,106}, 138377);
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
}
