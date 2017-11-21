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

public class contactsportal extends Activity implements B4AActivity{
	public static contactsportal mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "nikohealthy.com", "nikohealthy.com.contactsportal");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (contactsportal).");
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
		activityBA = new BA(this, layout, processBA, "nikohealthy.com", "nikohealthy.com.contactsportal");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "nikohealthy.com.contactsportal", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (contactsportal) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (contactsportal) Resume **");
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
		return contactsportal.class;
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
        BA.LogInfo("** Activity (contactsportal) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (contactsportal) Resume **");
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
public static String _vv5 = "";
public static String _vvvvvvvvvvvvv1 = "";
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollview1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _vvvvvvvvvvvv3 = null;
public static String _vv6 = "";
public static String _vv7 = "";
public static String _vv0 = "";
public static String _vvv1 = "";
public static String _vvvvvvvvvvvv0 = "";
public nikohealthy.com.main _vvvvvv2 = null;
public nikohealthy.com.httputils2service _vvvvvv3 = null;
public nikohealthy.com.global _vvvvvv4 = null;
public nikohealthy.com.signin _vvvvvv5 = null;
public nikohealthy.com.cancercare _vvvvvv6 = null;
public nikohealthy.com.login _vvvvvv7 = null;
public nikohealthy.com.loginselecto _vvvvvv0 = null;
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
 //BA.debugLineNum = 26;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 29;BA.debugLine="Activity.LoadLayout(\"contacts\")";
mostCurrent._activity.LoadLayout("contacts",mostCurrent.activityBA);
 //BA.debugLineNum = 30;BA.debugLine="ProgressDialogShow(\"Loading specialists, please";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Loading specialists, please wait...");
 //BA.debugLineNum = 31;BA.debugLine="fillscrollview";
_vvvvvvvvvvvv7();
 //BA.debugLineNum = 32;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 38;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 40;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 34;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
public static String  _btn_click() throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _b = null;
 //BA.debugLineNum = 191;BA.debugLine="Sub btn_Click";
 //BA.debugLineNum = 192;BA.debugLine="Dim b As Button";
_b = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 193;BA.debugLine="b=Sender";
_b.setObject((android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 //BA.debugLineNum = 194;BA.debugLine="myspecid=b.Tag";
_vv5 = BA.ObjectToString(_b.getTag());
 //BA.debugLineNum = 196;BA.debugLine="StartActivity(cancercare)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvv6.getObject()));
 //BA.debugLineNum = 197;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return "";
}
public static String  _btnback_click() throws Exception{
 //BA.debugLineNum = 186;BA.debugLine="Sub btnBack_Click";
 //BA.debugLineNum = 187;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvv2.getObject()));
 //BA.debugLineNum = 188;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 189;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvv4(String _query,String _jobname) throws Exception{
nikohealthy.com.httpjob _job = null;
 //BA.debugLineNum = 50;BA.debugLine="Sub ExecuteRemoteQuery(Query As String, JobName As";
 //BA.debugLineNum = 51;BA.debugLine="Dim job As HttpJob";
_job = new nikohealthy.com.httpjob();
 //BA.debugLineNum = 52;BA.debugLine="job.Initialize(JobName, Me)";
_job._initialize(processBA,_jobname,contactsportal.getObject());
 //BA.debugLineNum = 53;BA.debugLine="job.PostString(global.API,Query)";
_job._vvvv7(mostCurrent._vvvvvv4._v6,_query);
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvv7() throws Exception{
String _query = "";
 //BA.debugLineNum = 42;BA.debugLine="Sub fillscrollview";
 //BA.debugLineNum = 43;BA.debugLine="Dim query=\"SELECT * FROM specialist WHERE Specia";
_query = "SELECT * FROM specialist WHERE Specialization='"+mostCurrent._vvvvvvvvvvvv0+"'";
 //BA.debugLineNum = 44;BA.debugLine="ExecuteRemoteQuery(query, contactsspec)";
_vvvvvvv4(_query,_vvvvvvvvvvvvv1);
 //BA.debugLineNum = 48;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 17;BA.debugLine="Private ScrollView1 As ScrollView";
mostCurrent._scrollview1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim scvpanel As Panel";
mostCurrent._vvvvvvvvvvvv3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim userid=Main.userid As String";
mostCurrent._vv6 = mostCurrent._vvvvvv2._vv6;
 //BA.debugLineNum = 20;BA.debugLine="Dim entity=Main.entity As String";
mostCurrent._vv7 = mostCurrent._vvvvvv2._vv7;
 //BA.debugLineNum = 21;BA.debugLine="Dim myid=Main.myid As String";
mostCurrent._vv0 = mostCurrent._vvvvvv2._vv0;
 //BA.debugLineNum = 22;BA.debugLine="Dim mytask=Main.mytask As String";
mostCurrent._vvv1 = mostCurrent._vvvvvv2._vvv1;
 //BA.debugLineNum = 23;BA.debugLine="Dim myspec=Main.spec As String";
mostCurrent._vvvvvvvvvvvv0 = mostCurrent._vvvvvv2._vvv2;
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(nikohealthy.com.httpjob _job) throws Exception{
anywheresoftware.b4a.objects.StringUtils _u = null;
String _res = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _parameters = null;
anywheresoftware.b4a.objects.collections.Map _rootmap = null;
String _myuserid = "";
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
String _onlinestatus = "";
String _image = "";
String _updatedat = "";
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _userimage = null;
int _paneltop = 0;
int _panelheight = 0;
int _lblthreadstop = 0;
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _m = null;
anywheresoftware.b4a.objects.PanelWrapper _subpanel = null;
anywheresoftware.b4a.objects.LabelWrapper _lblusername = null;
anywheresoftware.b4a.objects.LabelWrapper _lblstatus = null;
anywheresoftware.b4a.objects.LabelWrapper _lblemail = null;
anywheresoftware.b4a.objects.ImageViewWrapper _myimageview = null;
anywheresoftware.b4a.objects.ButtonWrapper _btn = null;
String _myuser = "";
String _myemail = "";
String _mystatus = "";
 //BA.debugLineNum = 56;BA.debugLine="Sub JobDone(Job As HttpJob)";
 //BA.debugLineNum = 57;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 58;BA.debugLine="Dim u As StringUtils";
_u = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 59;BA.debugLine="Try";
try { //BA.debugLineNum = 60;BA.debugLine="If Job.Success Then";
if (_job._vvvvv2) { 
 //BA.debugLineNum = 61;BA.debugLine="Dim res As String";
_res = "";
 //BA.debugLineNum = 62;BA.debugLine="res = Job.GetString";
_res = _job._vvvv3();
 //BA.debugLineNum = 63;BA.debugLine="Log(\"Response from server: \" & res)";
anywheresoftware.b4a.keywords.Common.Log("Response from server: "+_res);
 //BA.debugLineNum = 64;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 65;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
 //BA.debugLineNum = 66;BA.debugLine="If Job.JobName==contactsspec Then";
if ((_job._vvvvv1).equals(_vvvvvvvvvvvvv1)) { 
 //BA.debugLineNum = 67;BA.debugLine="Dim PARAMETERS As List";
_parameters = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 68;BA.debugLine="PARAMETERS = parser.NextArray 'returns a list";
_parameters = _parser.NextArray();
 //BA.debugLineNum = 69;BA.debugLine="For Each rootMap As Map In PARAMETERS";
_rootmap = new anywheresoftware.b4a.objects.collections.Map();
final anywheresoftware.b4a.BA.IterableList group13 = _parameters;
final int groupLen13 = group13.getSize();
for (int index13 = 0;index13 < groupLen13 ;index13++){
_rootmap.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group13.Get(index13)));
 //BA.debugLineNum = 70;BA.debugLine="Dim myuserid As String=rootMap.Get(\"Spec_id\")";
_myuserid = BA.ObjectToString(_rootmap.Get((Object)("Spec_id")));
 //BA.debugLineNum = 71;BA.debugLine="Dim firstname As String=rootMap.Get(\"FirstName";
_firstname = BA.ObjectToString(_rootmap.Get((Object)("FirstName")));
 //BA.debugLineNum = 72;BA.debugLine="Dim middlename As String=rootMap.Get(\"MiddleNa";
_middlename = BA.ObjectToString(_rootmap.Get((Object)("MiddleName")));
 //BA.debugLineNum = 73;BA.debugLine="Dim lastname As String =rootMap.Get(\"LastName\"";
_lastname = BA.ObjectToString(_rootmap.Get((Object)("LastName")));
 //BA.debugLineNum = 74;BA.debugLine="Dim age As String=rootMap.Get(\"Age\")";
_age = BA.ObjectToString(_rootmap.Get((Object)("Age")));
 //BA.debugLineNum = 75;BA.debugLine="Dim maritalstatus As String=rootMap.Get(\"marit";
_maritalstatus = BA.ObjectToString(_rootmap.Get((Object)("maritalstatus")));
 //BA.debugLineNum = 76;BA.debugLine="Dim phone As String=rootMap.Get(\"Phone\")";
_phone = BA.ObjectToString(_rootmap.Get((Object)("Phone")));
 //BA.debugLineNum = 77;BA.debugLine="Dim email As String=rootMap.Get(\"Email\")";
_email = BA.ObjectToString(_rootmap.Get((Object)("Email")));
 //BA.debugLineNum = 78;BA.debugLine="Dim username As String=rootMap.Get(\"Username\")";
_username = BA.ObjectToString(_rootmap.Get((Object)("Username")));
 //BA.debugLineNum = 79;BA.debugLine="Dim password As String=rootMap.Get(\"Password\")";
_password = BA.ObjectToString(_rootmap.Get((Object)("Password")));
 //BA.debugLineNum = 80;BA.debugLine="Dim gender As String=rootMap.Get(\"Gender\")";
_gender = BA.ObjectToString(_rootmap.Get((Object)("Gender")));
 //BA.debugLineNum = 81;BA.debugLine="Dim residence As String=rootMap.Get(\"Residence";
_residence = BA.ObjectToString(_rootmap.Get((Object)("Residence")));
 //BA.debugLineNum = 82;BA.debugLine="Dim weight As String=rootMap.Get(\"weight\")";
_weight = BA.ObjectToString(_rootmap.Get((Object)("weight")));
 //BA.debugLineNum = 83;BA.debugLine="Dim height As String=rootMap.Get(\"height\")";
_height = BA.ObjectToString(_rootmap.Get((Object)("height")));
 //BA.debugLineNum = 84;BA.debugLine="Dim task As String=rootMap.Get(\"Specialization";
_task = BA.ObjectToString(_rootmap.Get((Object)("Specialization")));
 //BA.debugLineNum = 85;BA.debugLine="Dim medicalhistory As String=rootMap.Get(\"medi";
_medicalhistory = BA.ObjectToString(_rootmap.Get((Object)("medicalhistory")));
 //BA.debugLineNum = 86;BA.debugLine="Dim onlinestatus As String=rootMap.Get(\"status";
_onlinestatus = BA.ObjectToString(_rootmap.Get((Object)("status")));
 //BA.debugLineNum = 87;BA.debugLine="Dim image As String=rootMap.Get(\"Image\")";
_image = BA.ObjectToString(_rootmap.Get((Object)("Image")));
 //BA.debugLineNum = 88;BA.debugLine="Dim updatedat As String=rootMap.Get(\"Updated_a";
_updatedat = BA.ObjectToString(_rootmap.Get((Object)("Updated_at")));
 }
;
 //BA.debugLineNum = 92;BA.debugLine="Dim userimage As Bitmap";
_userimage = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 93;BA.debugLine="Dim PanelTop,PanelHeight,lblthreadsTop As Int";
_paneltop = 0;
_panelheight = 0;
_lblthreadstop = 0;
 //BA.debugLineNum = 95;BA.debugLine="userimage.Initialize(File.DirAssets,\"defaultima";
_userimage.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"defaultimagespec.png");
 //BA.debugLineNum = 96;BA.debugLine="PanelTop=0";
_paneltop = (int) (0);
 //BA.debugLineNum = 97;BA.debugLine="scvpanel=ScrollView1.Panel";
mostCurrent._vvvvvvvvvvvv3 = mostCurrent._scrollview1.getPanel();
 //BA.debugLineNum = 99;BA.debugLine="For i=0 To PARAMETERS.Size-1";
{
final int step39 = 1;
final int limit39 = (int) (_parameters.getSize()-1);
for (_i = (int) (0) ; (step39 > 0 && _i <= limit39) || (step39 < 0 && _i >= limit39); _i = ((int)(0 + _i + step39)) ) {
 //BA.debugLineNum = 100;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 101;BA.debugLine="m = PARAMETERS.Get(i)";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_parameters.Get(_i)));
 //BA.debugLineNum = 103;BA.debugLine="Dim subpanel As Panel";
_subpanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 104;BA.debugLine="Dim lblusername As Label";
_lblusername = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 105;BA.debugLine="Dim lblstatus As Label";
_lblstatus = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 106;BA.debugLine="Dim lblemail As  Label";
_lblemail = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 107;BA.debugLine="Dim myimageview As ImageView";
_myimageview = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 108;BA.debugLine="Dim btn As Button";
_btn = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 110;BA.debugLine="subpanel.Initialize(\"subpanel\")";
_subpanel.Initialize(mostCurrent.activityBA,"subpanel");
 //BA.debugLineNum = 112;BA.debugLine="PanelHeight=74dip";
_panelheight = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (74));
 //BA.debugLineNum = 113;BA.debugLine="lblthreadsTop=53dip";
_lblthreadstop = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (53));
 //BA.debugLineNum = 119;BA.debugLine="scvpanel.AddView(subpanel,0,PanelTop,ScrollView";
mostCurrent._vvvvvvvvvvvv3.AddView((android.view.View)(_subpanel.getObject()),(int) (0),_paneltop,mostCurrent._scrollview1.getWidth(),_panelheight);
 //BA.debugLineNum = 120;BA.debugLine="subpanel.Color=Colors.ARGB(0,0,0,0)";
_subpanel.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 122;BA.debugLine="Dim myuser As String";
_myuser = "";
 //BA.debugLineNum = 123;BA.debugLine="myuser=m.Get(\"Username\")";
_myuser = BA.ObjectToString(_m.Get((Object)("Username")));
 //BA.debugLineNum = 125;BA.debugLine="lblusername.Initialize(\"\")";
_lblusername.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 126;BA.debugLine="subpanel.AddView(lblusername,80dip,5dip,240dip,";
_subpanel.AddView((android.view.View)(_lblusername.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (240)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 127;BA.debugLine="lblusername.TextSize=18";
_lblusername.setTextSize((float) (18));
 //BA.debugLineNum = 128;BA.debugLine="lblusername.Tag=\"lblusername\"";
_lblusername.setTag((Object)("lblusername"));
 //BA.debugLineNum = 129;BA.debugLine="lblusername.Text=myuser";
_lblusername.setText((Object)(_myuser));
 //BA.debugLineNum = 130;BA.debugLine="lblusername.Color=Colors.ARGB(0,0,0,0)";
_lblusername.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 131;BA.debugLine="lblusername.TextColor=Colors.Black";
_lblusername.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 133;BA.debugLine="Dim myemail As String";
_myemail = "";
 //BA.debugLineNum = 134;BA.debugLine="myemail=m.Get(\"Email\")";
_myemail = BA.ObjectToString(_m.Get((Object)("Email")));
 //BA.debugLineNum = 137;BA.debugLine="lblemail.Initialize(\"\")";
_lblemail.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 138;BA.debugLine="subpanel.AddView(lblemail,80dip,lblthreadsTop,2";
_subpanel.AddView((android.view.View)(_lblemail.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),_lblthreadstop,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (240)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 139;BA.debugLine="lblemail.TextSize=16";
_lblemail.setTextSize((float) (16));
 //BA.debugLineNum = 140;BA.debugLine="lblemail.Tag=\"lblemail\"";
_lblemail.setTag((Object)("lblemail"));
 //BA.debugLineNum = 141;BA.debugLine="lblemail.Text=myemail";
_lblemail.setText((Object)(_myemail));
 //BA.debugLineNum = 142;BA.debugLine="lblemail.Color=Colors.ARGB(0,0,0,0)";
_lblemail.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 143;BA.debugLine="lblemail.TextColor=Colors.Blue";
_lblemail.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 145;BA.debugLine="myimageview.Initialize(\"\")";
_myimageview.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 146;BA.debugLine="subpanel.AddView(myimageview,5dip,5dip,65dip,65";
_subpanel.AddView((android.view.View)(_myimageview.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (65)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (65)));
 //BA.debugLineNum = 147;BA.debugLine="myimageview.Bitmap=userimage";
_myimageview.setBitmap((android.graphics.Bitmap)(_userimage.getObject()));
 //BA.debugLineNum = 148;BA.debugLine="myimageview.Gravity=Gravity.FILL";
_myimageview.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 151;BA.debugLine="Dim mystatus As String";
_mystatus = "";
 //BA.debugLineNum = 152;BA.debugLine="mystatus=m.Get(\"status\")";
_mystatus = BA.ObjectToString(_m.Get((Object)("status")));
 //BA.debugLineNum = 154;BA.debugLine="lblstatus.Initialize(\"\")";
_lblstatus.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 155;BA.debugLine="subpanel.AddView(lblstatus,80dip,27dip,240dip,3";
_subpanel.AddView((android.view.View)(_lblstatus.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (27)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (240)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 156;BA.debugLine="lblstatus.TextSize=14";
_lblstatus.setTextSize((float) (14));
 //BA.debugLineNum = 157;BA.debugLine="lblstatus.Tag=\"lblstatus\"";
_lblstatus.setTag((Object)("lblstatus"));
 //BA.debugLineNum = 158;BA.debugLine="lblstatus.Text=mystatus";
_lblstatus.setText((Object)(_mystatus));
 //BA.debugLineNum = 159;BA.debugLine="lblstatus.Color=Colors.ARGB(0,0,0,0)";
_lblstatus.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 160;BA.debugLine="lblstatus.TextColor=Colors.Green";
_lblstatus.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Green);
 //BA.debugLineNum = 164;BA.debugLine="myspecid=m.Get(\"Spec_id\")";
_vv5 = BA.ObjectToString(_m.Get((Object)("Spec_id")));
 //BA.debugLineNum = 165;BA.debugLine="btn.Initialize(\"btn\")";
_btn.Initialize(mostCurrent.activityBA,"btn");
 //BA.debugLineNum = 166;BA.debugLine="btn.Tag=myspecid";
_btn.setTag((Object)(_vv5));
 //BA.debugLineNum = 167;BA.debugLine="subpanel.AddView(btn,250dip,20dip,70dip,40dip)";
_subpanel.AddView((android.view.View)(_btn.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (250)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 168;BA.debugLine="btn.Text=\"Chat!\"";
_btn.setText((Object)("Chat!"));
 //BA.debugLineNum = 170;BA.debugLine="PanelTop=PanelTop+PanelHeight+50dip";
_paneltop = (int) (_paneltop+_panelheight+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 }
};
 //BA.debugLineNum = 172;BA.debugLine="scvpanel.Height=PanelTop";
mostCurrent._vvvvvvvvvvvv3.setHeight(_paneltop);
 };
 }else {
 //BA.debugLineNum = 177;BA.debugLine="ToastMessageShow(\"Failed, make sure you have an";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Failed, make sure you have an internet connection.",anywheresoftware.b4a.keywords.Common.True);
 };
 } 
       catch (Exception e97) {
			processBA.setLastException(e97); //BA.debugLineNum = 181;BA.debugLine="ToastMessageShow(\"No Specialists Found\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("No Specialists Found",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 183;BA.debugLine="Job.Release";
_job._vvvv0();
 //BA.debugLineNum = 184;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="Dim myspecid As String";
_vv5 = "";
 //BA.debugLineNum = 11;BA.debugLine="Private contactsspec=\"CSPEC\" As String";
_vvvvvvvvvvvvv1 = BA.__b (new byte[] {70,73,-22,48,76}, 773462);
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
}
