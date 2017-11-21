package nikohealthy.com;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class login extends Activity implements B4AActivity{
	public static login mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "nikohealthy.com", "nikohealthy.com.login");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (login).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, true))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "nikohealthy.com", "nikohealthy.com.login");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "nikohealthy.com.login", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (login) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (login) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return login.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (login) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (login) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        Object[] o;
        if (permissions.length > 0)
            o = new Object[] {permissions[0], grantResults[0] == 0};
        else
            o = new Object[] {"", false};
        processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static String _vvvvvvvvvvvv6 = "";
public static boolean _vv2 = false;
public anywheresoftware.b4a.objects.EditTextWrapper _txtusername = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpassword = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnlogin = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnsignup = null;
public static String _vvvvvvvvvvvv4 = "";
public static String _vvvvvvvvvvvv5 = "";
public static String _vv7 = "";
public static String _vv0 = "";
public static String _vvv1 = "";
public anywheresoftware.b4a.objects.LabelWrapper _lblshow = null;
public nikohealthy.com.main _vvvvvv2 = null;
public nikohealthy.com.httputils2service _vvvvvv3 = null;
public nikohealthy.com.global _vvvvvv4 = null;
public nikohealthy.com.signin _vvvvvv5 = null;
public nikohealthy.com.cancercare _vvvvvv6 = null;
public nikohealthy.com.loginselecto _vvvvvv0 = null;
public nikohealthy.com.contactsportal _vvvvvvv1 = null;
public nikohealthy.com.contactsportalusers _vvvvvvv2 = null;
public nikohealthy.com.signinspec _vvvvvvv3 = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 32;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 35;BA.debugLine="Activity.LoadLayout(\"main\")";
mostCurrent._activity.LoadLayout("main",mostCurrent.activityBA);
 //BA.debugLineNum = 37;BA.debugLine="If loginselecto.userbuttonpressed==True Then";
if (mostCurrent._vvvvvv0._vv3==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 38;BA.debugLine="entity=\"user\"";
mostCurrent._vv7 = "user";
 //BA.debugLineNum = 39;BA.debugLine="myid=\"User_id\"";
mostCurrent._vv0 = "User_id";
 //BA.debugLineNum = 40;BA.debugLine="mytask=\"occupation\"";
mostCurrent._vvv1 = "occupation";
 //BA.debugLineNum = 41;BA.debugLine="lblshow.Text=\"Log in as \"&entity";
mostCurrent._lblshow.setText((Object)("Log in as "+mostCurrent._vv7));
 }else if(mostCurrent._vvvvvv0._vv4==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 43;BA.debugLine="entity=\"specialist\"";
mostCurrent._vv7 = "specialist";
 //BA.debugLineNum = 44;BA.debugLine="myid=\"Spec_id\"";
mostCurrent._vv0 = "Spec_id";
 //BA.debugLineNum = 45;BA.debugLine="mytask=\"Specialization\"";
mostCurrent._vvv1 = "Specialization";
 //BA.debugLineNum = 46;BA.debugLine="lblshow.Text=\"Log in as \"&entity";
mostCurrent._lblshow.setText((Object)("Log in as "+mostCurrent._vv7));
 //BA.debugLineNum = 47;BA.debugLine="btnsignup.Visible=False";
mostCurrent._btnsignup.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 155;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 156;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 157;BA.debugLine="StartActivity(loginselecto)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvv0.getObject()));
 //BA.debugLineNum = 158;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 159;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 161;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 163;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 55;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 57;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 51;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 53;BA.debugLine="End Sub";
return "";
}
public static String  _btnforgotpass_click() throws Exception{
 //BA.debugLineNum = 165;BA.debugLine="Sub btnforgotpass_Click";
 //BA.debugLineNum = 166;BA.debugLine="ProgressDialogShow(\"Please Wait...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Please Wait...");
 //BA.debugLineNum = 167;BA.debugLine="ToastMessageShow(\"Password reset information sent";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Password reset information sent to your registered email",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 168;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvv4(String _query,String _jobname) throws Exception{
nikohealthy.com.httpjob _job = null;
 //BA.debugLineNum = 88;BA.debugLine="Sub ExecuteRemoteQuery(Query As String, JobName As";
 //BA.debugLineNum = 89;BA.debugLine="Dim job As HttpJob";
_job = new nikohealthy.com.httpjob();
 //BA.debugLineNum = 90;BA.debugLine="job.Initialize(JobName, Me)";
_job._initialize(processBA,_jobname,login.getObject());
 //BA.debugLineNum = 91;BA.debugLine="job.PostString(global.API,Query)";
_job._vvvv7(mostCurrent._vvvvvv4._v6,_query);
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private txtusername As EditText";
mostCurrent._txtusername = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private txtpassword As EditText";
mostCurrent._txtpassword = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private btnlogin As Button";
mostCurrent._btnlogin = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private btnsignup As Button";
mostCurrent._btnsignup = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private myusername As String";
mostCurrent._vvvvvvvvvvvv4 = "";
 //BA.debugLineNum = 21;BA.debugLine="Private mypassword As String";
mostCurrent._vvvvvvvvvvvv5 = "";
 //BA.debugLineNum = 24;BA.debugLine="Dim entity As String";
mostCurrent._vv7 = "";
 //BA.debugLineNum = 25;BA.debugLine="Dim myid As String";
mostCurrent._vv0 = "";
 //BA.debugLineNum = 26;BA.debugLine="Dim mytask As String";
mostCurrent._vvv1 = "";
 //BA.debugLineNum = 27;BA.debugLine="Private lblshow As Label";
mostCurrent._lblshow = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(nikohealthy.com.httpjob _job) throws Exception{
anywheresoftware.b4a.objects.StringUtils _u = null;
String _res = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _parameters = null;
anywheresoftware.b4a.objects.collections.Map _rootmap = null;
String _userid = "";
String _firstname = "";
String _middlename = "";
String _lastname = "";
String _age = "";
String _maritalstatus = "";
String _phone = "";
String _email = "";
String _username = "";
String _password = "";
String _gender = "";
String _residence = "";
String _weight = "";
String _height = "";
String _task = "";
String _medicalhistory = "";
String _image = "";
String _updatedat = "";
 //BA.debugLineNum = 94;BA.debugLine="Sub JobDone(Job As HttpJob)";
 //BA.debugLineNum = 95;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 96;BA.debugLine="Dim u As StringUtils";
_u = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 97;BA.debugLine="Try";
try { //BA.debugLineNum = 98;BA.debugLine="If Job.Success Then";
if (_job._vvvvv2) { 
 //BA.debugLineNum = 99;BA.debugLine="Dim res As String";
_res = "";
 //BA.debugLineNum = 100;BA.debugLine="res = Job.GetString";
_res = _job._vvvv3();
 //BA.debugLineNum = 101;BA.debugLine="Log(\"Response from server: \" & res)";
anywheresoftware.b4a.keywords.Common.Log("Response from server: "+_res);
 //BA.debugLineNum = 102;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 103;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
 //BA.debugLineNum = 104;BA.debugLine="If Job.JobName==mylogin Then";
if ((_job._vvvvv1).equals(_vvvvvvvvvvvv6)) { 
 //BA.debugLineNum = 105;BA.debugLine="Dim PARAMETERS As List";
_parameters = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 106;BA.debugLine="PARAMETERS = parser.NextArray 'returns a list";
_parameters = _parser.NextArray();
 //BA.debugLineNum = 107;BA.debugLine="For Each rootMap As Map In PARAMETERS";
_rootmap = new anywheresoftware.b4a.objects.collections.Map();
final anywheresoftware.b4a.BA.IterableList group13 = _parameters;
final int groupLen13 = group13.getSize();
for (int index13 = 0;index13 < groupLen13 ;index13++){
_rootmap.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group13.Get(index13)));
 //BA.debugLineNum = 108;BA.debugLine="Dim userid As String=rootMap.Get(myid)";
_userid = BA.ObjectToString(_rootmap.Get((Object)(mostCurrent._vv0)));
 //BA.debugLineNum = 109;BA.debugLine="Dim firstname As String=rootMap.Get(\"FirstName";
_firstname = BA.ObjectToString(_rootmap.Get((Object)("FirstName")));
 //BA.debugLineNum = 110;BA.debugLine="Dim middlename As String=rootMap.Get(\"MiddleNa";
_middlename = BA.ObjectToString(_rootmap.Get((Object)("MiddleName")));
 //BA.debugLineNum = 111;BA.debugLine="Dim lastname As String =rootMap.Get(\"LastName\"";
_lastname = BA.ObjectToString(_rootmap.Get((Object)("LastName")));
 //BA.debugLineNum = 112;BA.debugLine="Dim age As String=rootMap.Get(\"Age\")";
_age = BA.ObjectToString(_rootmap.Get((Object)("Age")));
 //BA.debugLineNum = 113;BA.debugLine="Dim maritalstatus As String=rootMap.Get(\"marit";
_maritalstatus = BA.ObjectToString(_rootmap.Get((Object)("maritalstatus")));
 //BA.debugLineNum = 114;BA.debugLine="Dim phone As String=rootMap.Get(\"Phone\")";
_phone = BA.ObjectToString(_rootmap.Get((Object)("Phone")));
 //BA.debugLineNum = 115;BA.debugLine="Dim email As String=rootMap.Get(\"Email\")";
_email = BA.ObjectToString(_rootmap.Get((Object)("Email")));
 //BA.debugLineNum = 116;BA.debugLine="Dim username As String=rootMap.Get(\"Username\")";
_username = BA.ObjectToString(_rootmap.Get((Object)("Username")));
 //BA.debugLineNum = 117;BA.debugLine="Dim password As String=rootMap.Get(\"Password\")";
_password = BA.ObjectToString(_rootmap.Get((Object)("Password")));
 //BA.debugLineNum = 118;BA.debugLine="Dim gender As String=rootMap.Get(\"Gender\")";
_gender = BA.ObjectToString(_rootmap.Get((Object)("Gender")));
 //BA.debugLineNum = 119;BA.debugLine="Dim residence As String=rootMap.Get(\"Residence";
_residence = BA.ObjectToString(_rootmap.Get((Object)("Residence")));
 //BA.debugLineNum = 120;BA.debugLine="Dim weight As String=rootMap.Get(\"weight\")";
_weight = BA.ObjectToString(_rootmap.Get((Object)("weight")));
 //BA.debugLineNum = 121;BA.debugLine="Dim height As String=rootMap.Get(\"height\")";
_height = BA.ObjectToString(_rootmap.Get((Object)("height")));
 //BA.debugLineNum = 122;BA.debugLine="Dim task As String=rootMap.Get(mytask)";
_task = BA.ObjectToString(_rootmap.Get((Object)(mostCurrent._vvv1)));
 //BA.debugLineNum = 123;BA.debugLine="Dim medicalhistory As String=rootMap.Get(\"medi";
_medicalhistory = BA.ObjectToString(_rootmap.Get((Object)("medicalhistory")));
 //BA.debugLineNum = 124;BA.debugLine="Dim image As String=rootMap.Get(\"Image\")";
_image = BA.ObjectToString(_rootmap.Get((Object)("Image")));
 //BA.debugLineNum = 125;BA.debugLine="Dim updatedat As String=rootMap.Get(\"Updated_a";
_updatedat = BA.ObjectToString(_rootmap.Get((Object)("Updated_at")));
 }
;
 //BA.debugLineNum = 128;BA.debugLine="logged=True";
_vv2 = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 129;BA.debugLine="File.WriteString(File.DirInternal,\"session\",u.En";
anywheresoftware.b4a.keywords.Common.File.WriteString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"session",_u.EncodeUrl(_userid,"UTF8")+"");
 //BA.debugLineNum = 130;BA.debugLine="File.WriteString(File.DirInternal,\"id\",Job.Ge";
anywheresoftware.b4a.keywords.Common.File.WriteString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"id",_job._vvvv3());
 //BA.debugLineNum = 132;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvv2.getObject()));
 //BA.debugLineNum = 133;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 }else {
 //BA.debugLineNum = 137;BA.debugLine="ToastMessageShow(\"Failed, make sure you have an";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Failed, make sure you have an internet connection.",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 138;BA.debugLine="logged=False";
_vv2 = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e44) {
			processBA.setLastException(e44); //BA.debugLineNum = 141;BA.debugLine="ToastMessageShow(\"Invalid login credentials\",True";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Invalid login credentials",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 142;BA.debugLine="logged=False";
_vv2 = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 144;BA.debugLine="Job.Release";
_job._vvvv0();
 //BA.debugLineNum = 145;BA.debugLine="End Sub";
return "";
}
public static String  _loginbtn_click() throws Exception{
String _query = "";
 //BA.debugLineNum = 61;BA.debugLine="Sub loginbtn_Click";
 //BA.debugLineNum = 65;BA.debugLine="ProgressDialogShow(\"Please wait...\") 'oouuu young";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Please wait...");
 //BA.debugLineNum = 66;BA.debugLine="myusername=txtusername.Text";
mostCurrent._vvvvvvvvvvvv4 = mostCurrent._txtusername.getText();
 //BA.debugLineNum = 67;BA.debugLine="mypassword=txtpassword.Text";
mostCurrent._vvvvvvvvvvvv5 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 68;BA.debugLine="If myusername.Trim.Length==0 Then";
if (mostCurrent._vvvvvvvvvvvv4.trim().length()==0) { 
 //BA.debugLineNum = 69;BA.debugLine="ToastMessageShow(\"Please enter username\",True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Please enter username",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 71;BA.debugLine="If mypassword.Trim.Length==0 Then";
if (mostCurrent._vvvvvvvvvvvv5.trim().length()==0) { 
 //BA.debugLineNum = 72;BA.debugLine="ToastMessageShow(\"Please enter Password\",True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Please enter Password",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 74;BA.debugLine="If myusername==\"admin\" And mypassword=\"qwerty123\"";
if ((mostCurrent._vvvvvvvvvvvv4).equals("admin") && (mostCurrent._vvvvvvvvvvvv5).equals("qwerty123")) { 
 //BA.debugLineNum = 75;BA.debugLine="ToastMessageShow(\"Access to specialists registra";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Access to specialists registration granted",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 76;BA.debugLine="btnsignup.Visible=True";
mostCurrent._btnsignup.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 78;BA.debugLine="Dim query=\"SELECT * FROM \"&entity&\" WHERE Usernam";
_query = "SELECT * FROM "+mostCurrent._vv7+" WHERE Username='"+mostCurrent._vvvvvvvvvvvv4+"' AND Password='"+mostCurrent._vvvvvvvvvvvv5+"'";
 //BA.debugLineNum = 79;BA.debugLine="ExecuteRemoteQuery(query, mylogin)";
_vvvvvvv4(_query,_vvvvvvvvvvvv6);
 };
 //BA.debugLineNum = 81;BA.debugLine="If logged=True Then";
if (_vv2==anywheresoftware.b4a.keywords.Common.True) { 
 }else {
 };
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Private mylogin=\"login\" As String";
_vvvvvvvvvvvv6 = BA.__b (new byte[] {105,117,99,83,97}, 468480);
 //BA.debugLineNum = 10;BA.debugLine="Dim logged As Boolean";
_vv2 = false;
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _signupbtn_click() throws Exception{
 //BA.debugLineNum = 146;BA.debugLine="Sub signupbtn_Click";
 //BA.debugLineNum = 147;BA.debugLine="If loginselecto.userbuttonpressed Then";
if (mostCurrent._vvvvvv0._vv3) { 
 //BA.debugLineNum = 148;BA.debugLine="StartActivity(signin)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvv5.getObject()));
 }else if(mostCurrent._vvvvvv0._vv4) { 
 //BA.debugLineNum = 150;BA.debugLine="StartActivity(signinspec)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv3.getObject()));
 };
 //BA.debugLineNum = 152;BA.debugLine="End Sub";
return "";
}
}
