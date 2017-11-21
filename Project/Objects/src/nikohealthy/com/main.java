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

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "nikohealthy.com", "nikohealthy.com.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
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
		activityBA = new BA(this, layout, processBA, "nikohealthy.com", "nikohealthy.com.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "nikohealthy.com.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
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
		return main.class;
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
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (main) Resume **");
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
public static anywheresoftware.b4a.objects.Timer _v0 = null;
public static String _vvvvvvv6 = "";
public static String _vvvvvvv0 = "";
public static String _vvvvvvvv1 = "";
public static String _vvvvvvvv2 = "";
public static String _vvvvvvvv3 = "";
public static String _vvvvvvvv4 = "";
public static String _vvvvvvvv5 = "";
public static String _vvvvvvvv6 = "";
public static String _vvvvvvvv7 = "";
public static String _vvvvvvvv0 = "";
public static String _vvvvvvvvv1 = "";
public static String _vvvvvvvvv2 = "";
public static String _vvvvvvv5 = "";
public static String _vv6 = "";
public static String _vv7 = "";
public static String _vv0 = "";
public static String _vvv1 = "";
public static String _vvv2 = "";
public static String _vvv3 = "";
public static String _vvv4 = "";
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollview1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblusername = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblemail = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltime = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblthreadcancer = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblthreadnutrition = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblthreadweight = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblthreadphysical = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblthreaddrug = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblthreadalcohol = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblthreaddiabetes = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblthreadcardiac = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblthreadmetabolic = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblthreadorgan = null;
public anywheresoftware.b4a.objects.LabelWrapper _vvvvvvv7 = null;
public nikohealthy.com.httputils2service _vvvvvv3 = null;
public nikohealthy.com.global _vvvvvv4 = null;
public nikohealthy.com.signin _vvvvvv5 = null;
public nikohealthy.com.cancercare _vvvvvv6 = null;
public nikohealthy.com.login _vvvvvv7 = null;
public nikohealthy.com.loginselecto _vvvvvv0 = null;
public nikohealthy.com.contactsportal _vvvvvvv1 = null;
public nikohealthy.com.contactsportalusers _vvvvvvv2 = null;
public nikohealthy.com.signinspec _vvvvvvv3 = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (signin.mostCurrent != null);
vis = vis | (cancercare.mostCurrent != null);
vis = vis | (login.mostCurrent != null);
vis = vis | (loginselecto.mostCurrent != null);
vis = vis | (contactsportal.mostCurrent != null);
vis = vis | (contactsportalusers.mostCurrent != null);
vis = vis | (signinspec.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 67;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 69;BA.debugLine="If Not(File.Exists(File.DirInternal,\"session\")) T";
if (anywheresoftware.b4a.keywords.Common.Not(anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"session"))) { 
 //BA.debugLineNum = 70;BA.debugLine="StartActivity(loginselecto)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvv0.getObject()));
 }else {
 //BA.debugLineNum = 72;BA.debugLine="login.logged=True";
mostCurrent._vvvvvv7._vv2 = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 74;BA.debugLine="Activity.LoadLayout(\"homescreen\")";
mostCurrent._activity.LoadLayout("homescreen",mostCurrent.activityBA);
 //BA.debugLineNum = 75;BA.debugLine="ScrollView1.Panel.LoadLayout(\"homepanels\")";
mostCurrent._scrollview1.getPanel().LoadLayout("homepanels",mostCurrent.activityBA);
 //BA.debugLineNum = 76;BA.debugLine="tm.Initialize(\"hometime\",1000)";
_v0.Initialize(processBA,"hometime",(long) (1000));
 //BA.debugLineNum = 77;BA.debugLine="tm.Enabled=True";
_v0.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 78;BA.debugLine="Activity.AddMenuItem(\"Profile\",\"profile\")";
mostCurrent._activity.AddMenuItem((java.lang.CharSequence)("Profile"),"profile");
 //BA.debugLineNum = 79;BA.debugLine="Activity.AddMenuItem(\"LogOut\",\"logout\")";
mostCurrent._activity.AddMenuItem((java.lang.CharSequence)("LogOut"),"logout");
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
String _updatequery = "";
 //BA.debugLineNum = 253;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 254;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 256;BA.debugLine="Dim updatequery=\"UPDATE \"&entity&\" SET STATUS='o";
_updatequery = "UPDATE "+_vv7+" SET STATUS='offline' WHERE "+_vv0+"='"+_vv6+"'";
 //BA.debugLineNum = 257;BA.debugLine="ExecuteRemoteQuery(updatequery,status)";
_vvvvvvv4(_updatequery,_vvvvvvv5);
 //BA.debugLineNum = 258;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 259;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 261;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 263;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 100;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 102;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
String _query = "";
String _updatequery = "";
 //BA.debugLineNum = 83;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 84;BA.debugLine="If loginselecto.userbuttonpressed==True Then";
if (mostCurrent._vvvvvv0._vv3==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 85;BA.debugLine="entity=\"user\"";
_vv7 = "user";
 //BA.debugLineNum = 86;BA.debugLine="myid=\"User_id\"";
_vv0 = "User_id";
 //BA.debugLineNum = 87;BA.debugLine="mytask=\"occupation\"";
_vvv1 = "occupation";
 }else if(mostCurrent._vvvvvv0._vv4==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 89;BA.debugLine="entity=\"specialist\"";
_vv7 = "specialist";
 //BA.debugLineNum = 90;BA.debugLine="myid=\"Spec_id\"";
_vv0 = "Spec_id";
 //BA.debugLineNum = 91;BA.debugLine="mytask=\"Specialization\"";
_vvv1 = "Specialization";
 };
 //BA.debugLineNum = 93;BA.debugLine="userid=File.ReadString(File.DirInternal,\"session\"";
_vv6 = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"session");
 //BA.debugLineNum = 94;BA.debugLine="Dim query=\"SELECT * FROM \"&entity&\" WHERE \"&myid&";
_query = "SELECT * FROM "+_vv7+" WHERE "+_vv0+"='"+_vv6+"'";
 //BA.debugLineNum = 95;BA.debugLine="ExecuteRemoteQuery(query, home)";
_vvvvvvv4(_query,_vvvvvvv6);
 //BA.debugLineNum = 96;BA.debugLine="Dim updatequery=\"UPDATE \"&entity&\" SET STATUS='on";
_updatequery = "UPDATE "+_vv7+" SET STATUS='online' WHERE "+_vv0+"='"+_vv6+"'";
 //BA.debugLineNum = 97;BA.debugLine="ExecuteRemoteQuery(updatequery,status)";
_vvvvvvv4(_updatequery,_vvvvvvv5);
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
return "";
}
public static String  _btnback_click() throws Exception{
String _updatequery = "";
 //BA.debugLineNum = 242;BA.debugLine="Sub btnBack_Click";
 //BA.debugLineNum = 244;BA.debugLine="Dim updatequery=\"UPDATE \"&entity&\" SET STATUS='o";
_updatequery = "UPDATE "+_vv7+" SET STATUS='offline' WHERE "+_vv0+"='"+_vv6+"'";
 //BA.debugLineNum = 245;BA.debugLine="ExecuteRemoteQuery(updatequery,status)";
_vvvvvvv4(_updatequery,_vvvvvvv5);
 //BA.debugLineNum = 246;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 247;BA.debugLine="End Sub";
return "";
}
public static String  _btnchatalcohol_click() throws Exception{
 //BA.debugLineNum = 371;BA.debugLine="Sub btnchatalcohol_Click";
 //BA.debugLineNum = 372;BA.debugLine="spec=\"Alcohol Intoxication Control\"";
_vvv2 = "Alcohol Intoxication Control";
 //BA.debugLineNum = 373;BA.debugLine="tableuser=\"alcoholuser\"";
_vvv3 = "alcoholuser";
 //BA.debugLineNum = 374;BA.debugLine="tablespec=\"alcoholspec\"";
_vvv4 = "alcoholspec";
 //BA.debugLineNum = 375;BA.debugLine="If entity=\"user\" Then";
if ((_vv7).equals("user")) { 
 //BA.debugLineNum = 376;BA.debugLine="StartActivity(contactsportal)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv1.getObject()));
 //BA.debugLineNum = 377;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else if((_vv7).equals("specialist")) { 
 //BA.debugLineNum = 380;BA.debugLine="StartActivity(contactsportalusers)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv2.getObject()));
 //BA.debugLineNum = 381;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 383;BA.debugLine="End Sub";
return "";
}
public static String  _btnchatcancer_click() throws Exception{
 //BA.debugLineNum = 306;BA.debugLine="Sub btnchatcancer_Click";
 //BA.debugLineNum = 307;BA.debugLine="spec=\"Cancer Care & Prevention\"";
_vvv2 = "Cancer Care & Prevention";
 //BA.debugLineNum = 308;BA.debugLine="tableuser=\"cancercareuser\"";
_vvv3 = "cancercareuser";
 //BA.debugLineNum = 309;BA.debugLine="tablespec=\"cancercarespec\"";
_vvv4 = "cancercarespec";
 //BA.debugLineNum = 310;BA.debugLine="If entity=\"user\" Then";
if ((_vv7).equals("user")) { 
 //BA.debugLineNum = 311;BA.debugLine="StartActivity(contactsportal)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv1.getObject()));
 //BA.debugLineNum = 312;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else if((_vv7).equals("specialist")) { 
 //BA.debugLineNum = 315;BA.debugLine="StartActivity(contactsportalusers)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv2.getObject()));
 //BA.debugLineNum = 316;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 318;BA.debugLine="End Sub";
return "";
}
public static String  _btnchatcardiac_click() throws Exception{
 //BA.debugLineNum = 397;BA.debugLine="Sub btnchatcardiac_Click";
 //BA.debugLineNum = 398;BA.debugLine="spec=\"Cardiovascular Disease Control\"";
_vvv2 = "Cardiovascular Disease Control";
 //BA.debugLineNum = 399;BA.debugLine="tableuser=\"cardiacuser\"";
_vvv3 = "cardiacuser";
 //BA.debugLineNum = 400;BA.debugLine="tablespec=\"cardiacspec\"";
_vvv4 = "cardiacspec";
 //BA.debugLineNum = 401;BA.debugLine="If entity=\"user\" Then";
if ((_vv7).equals("user")) { 
 //BA.debugLineNum = 402;BA.debugLine="StartActivity(contactsportal)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv1.getObject()));
 //BA.debugLineNum = 403;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else if((_vv7).equals("specialist")) { 
 //BA.debugLineNum = 406;BA.debugLine="StartActivity(contactsportalusers)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv2.getObject()));
 //BA.debugLineNum = 407;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 409;BA.debugLine="End Sub";
return "";
}
public static String  _btnchatdiabetes_click() throws Exception{
 //BA.debugLineNum = 384;BA.debugLine="Sub btnchatdiabetes_Click";
 //BA.debugLineNum = 385;BA.debugLine="spec=\"Diabetes Corner\"";
_vvv2 = "Diabetes Corner";
 //BA.debugLineNum = 386;BA.debugLine="tableuser=\"diabetesuser\"";
_vvv3 = "diabetesuser";
 //BA.debugLineNum = 387;BA.debugLine="tablespec=\"diabetesspec\"";
_vvv4 = "diabetesspec";
 //BA.debugLineNum = 388;BA.debugLine="If entity=\"user\" Then";
if ((_vv7).equals("user")) { 
 //BA.debugLineNum = 389;BA.debugLine="StartActivity(contactsportal)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv1.getObject()));
 //BA.debugLineNum = 390;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else if((_vv7).equals("specialist")) { 
 //BA.debugLineNum = 393;BA.debugLine="StartActivity(contactsportalusers)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv2.getObject()));
 //BA.debugLineNum = 394;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 396;BA.debugLine="End Sub";
return "";
}
public static String  _btnchatdrug_click() throws Exception{
 //BA.debugLineNum = 358;BA.debugLine="Sub btnchatdrug_Click";
 //BA.debugLineNum = 359;BA.debugLine="spec=\"Drug & Substance Abuse Control\"";
_vvv2 = "Drug & Substance Abuse Control";
 //BA.debugLineNum = 360;BA.debugLine="tableuser=\"druguser\"";
_vvv3 = "druguser";
 //BA.debugLineNum = 361;BA.debugLine="tablespec=\"drugspec\"";
_vvv4 = "drugspec";
 //BA.debugLineNum = 362;BA.debugLine="If entity=\"user\" Then";
if ((_vv7).equals("user")) { 
 //BA.debugLineNum = 363;BA.debugLine="StartActivity(contactsportal)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv1.getObject()));
 //BA.debugLineNum = 364;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else if((_vv7).equals("specialist")) { 
 //BA.debugLineNum = 367;BA.debugLine="StartActivity(contactsportalusers)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv2.getObject()));
 //BA.debugLineNum = 368;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 370;BA.debugLine="End Sub";
return "";
}
public static String  _btnchatmetabolic_click() throws Exception{
 //BA.debugLineNum = 410;BA.debugLine="Sub btnchatmetabolic_Click";
 //BA.debugLineNum = 411;BA.debugLine="spec=\"Inherited Metabolic Disorders\"";
_vvv2 = "Inherited Metabolic Disorders";
 //BA.debugLineNum = 412;BA.debugLine="tableuser=\"metabolicuser\"";
_vvv3 = "metabolicuser";
 //BA.debugLineNum = 413;BA.debugLine="tablespec=\"metabolicspec\"";
_vvv4 = "metabolicspec";
 //BA.debugLineNum = 414;BA.debugLine="If entity=\"user\" Then";
if ((_vv7).equals("user")) { 
 //BA.debugLineNum = 415;BA.debugLine="StartActivity(contactsportal)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv1.getObject()));
 //BA.debugLineNum = 416;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else if((_vv7).equals("specialist")) { 
 //BA.debugLineNum = 419;BA.debugLine="StartActivity(contactsportalusers)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv2.getObject()));
 //BA.debugLineNum = 420;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 422;BA.debugLine="End Sub";
return "";
}
public static String  _btnchatnutrition_click() throws Exception{
 //BA.debugLineNum = 319;BA.debugLine="Sub btnchatnutrition_Click";
 //BA.debugLineNum = 320;BA.debugLine="spec=\"Nutrition Guide\"";
_vvv2 = "Nutrition Guide";
 //BA.debugLineNum = 321;BA.debugLine="tableuser=\"nutritionuser\"";
_vvv3 = "nutritionuser";
 //BA.debugLineNum = 322;BA.debugLine="tablespec=\"nutritionspec\"";
_vvv4 = "nutritionspec";
 //BA.debugLineNum = 323;BA.debugLine="If entity=\"user\" Then";
if ((_vv7).equals("user")) { 
 //BA.debugLineNum = 324;BA.debugLine="StartActivity(contactsportal)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv1.getObject()));
 //BA.debugLineNum = 325;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else if((_vv7).equals("specialist")) { 
 //BA.debugLineNum = 328;BA.debugLine="StartActivity(contactsportalusers)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv2.getObject()));
 //BA.debugLineNum = 329;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 331;BA.debugLine="End Sub";
return "";
}
public static String  _btnchatorgan_click() throws Exception{
 //BA.debugLineNum = 423;BA.debugLine="Sub btnchatorgan_Click";
 //BA.debugLineNum = 424;BA.debugLine="spec=\"Organ Care\"";
_vvv2 = "Organ Care";
 //BA.debugLineNum = 425;BA.debugLine="tableuser=\"organuser\"";
_vvv3 = "organuser";
 //BA.debugLineNum = 426;BA.debugLine="tablespec=\"organspec\"";
_vvv4 = "organspec";
 //BA.debugLineNum = 427;BA.debugLine="If entity=\"user\" Then";
if ((_vv7).equals("user")) { 
 //BA.debugLineNum = 428;BA.debugLine="StartActivity(contactsportal)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv1.getObject()));
 //BA.debugLineNum = 429;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else if((_vv7).equals("specialist")) { 
 //BA.debugLineNum = 432;BA.debugLine="StartActivity(contactsportalusers)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv2.getObject()));
 //BA.debugLineNum = 433;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 435;BA.debugLine="End Sub";
return "";
}
public static String  _btnchatphysical_click() throws Exception{
 //BA.debugLineNum = 345;BA.debugLine="Sub btnchatphysical_Click";
 //BA.debugLineNum = 346;BA.debugLine="spec=\"Physical Activity Guide\"";
_vvv2 = "Physical Activity Guide";
 //BA.debugLineNum = 347;BA.debugLine="tableuser=\"physicaluser\"";
_vvv3 = "physicaluser";
 //BA.debugLineNum = 348;BA.debugLine="tablespec=\"physicalspec\"";
_vvv4 = "physicalspec";
 //BA.debugLineNum = 349;BA.debugLine="If entity=\"user\" Then";
if ((_vv7).equals("user")) { 
 //BA.debugLineNum = 350;BA.debugLine="StartActivity(contactsportal)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv1.getObject()));
 //BA.debugLineNum = 351;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else if((_vv7).equals("specialist")) { 
 //BA.debugLineNum = 354;BA.debugLine="StartActivity(contactsportalusers)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv2.getObject()));
 //BA.debugLineNum = 355;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 357;BA.debugLine="End Sub";
return "";
}
public static String  _btnchatweight_click() throws Exception{
 //BA.debugLineNum = 332;BA.debugLine="Sub btnchatweight_Click";
 //BA.debugLineNum = 333;BA.debugLine="spec=\"Weight Management\"";
_vvv2 = "Weight Management";
 //BA.debugLineNum = 334;BA.debugLine="tableuser=\"weightuser\"";
_vvv3 = "weightuser";
 //BA.debugLineNum = 335;BA.debugLine="tablespec=\"weightspec\"";
_vvv4 = "weightspec";
 //BA.debugLineNum = 336;BA.debugLine="If entity=\"user\" Then";
if ((_vv7).equals("user")) { 
 //BA.debugLineNum = 337;BA.debugLine="StartActivity(contactsportal)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv1.getObject()));
 //BA.debugLineNum = 338;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else if((_vv7).equals("specialist")) { 
 //BA.debugLineNum = 341;BA.debugLine="StartActivity(contactsportalusers)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv2.getObject()));
 //BA.debugLineNum = 342;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 344;BA.debugLine="End Sub";
return "";
}
public static String  _btnlogout_click() throws Exception{
 //BA.debugLineNum = 439;BA.debugLine="Sub btnlogout_Click";
 //BA.debugLineNum = 440;BA.debugLine="File.Delete(File.DirInternal,\"session\")";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"session");
 //BA.debugLineNum = 441;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 442;BA.debugLine="End Sub";
return "";
}
public static String  _btnmedical_click() throws Exception{
 //BA.debugLineNum = 436;BA.debugLine="Sub btnmedical_Click";
 //BA.debugLineNum = 437;BA.debugLine="ToastMessageShow(\"Information update in progress.";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Information update in progress...",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 438;BA.debugLine="End Sub";
return "";
}
public static String  _btnmore_click() throws Exception{
 //BA.debugLineNum = 267;BA.debugLine="Sub btnmore_Click";
 //BA.debugLineNum = 268;BA.debugLine="ToastMessageShow(\"Use the options hardware button";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Use the options hardware button on your device",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 269;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvv4(String _query,String _jobname) throws Exception{
nikohealthy.com.httpjob _job = null;
 //BA.debugLineNum = 104;BA.debugLine="Sub ExecuteRemoteQuery(Query As String, JobName As";
 //BA.debugLineNum = 105;BA.debugLine="Dim job As HttpJob";
_job = new nikohealthy.com.httpjob();
 //BA.debugLineNum = 106;BA.debugLine="job.Initialize(JobName, Me)";
_job._initialize(processBA,_jobname,main.getObject());
 //BA.debugLineNum = 107;BA.debugLine="job.PostString(global.API,Query)";
_job._vvvv7(mostCurrent._vvvvvv4._v6,_query);
 //BA.debugLineNum = 108;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 42;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 47;BA.debugLine="Private ScrollView1 As ScrollView";
mostCurrent._scrollview1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private lblusername As Label";
mostCurrent._lblusername = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private lblemail As Label";
mostCurrent._lblemail = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private lbltime As Label";
mostCurrent._lbltime = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private lblthreadcancer As Label";
mostCurrent._lblthreadcancer = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private lblthreadnutrition As Label";
mostCurrent._lblthreadnutrition = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private lblthreadweight As Label";
mostCurrent._lblthreadweight = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Private lblthreadphysical As Label";
mostCurrent._lblthreadphysical = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private lblthreaddrug As Label";
mostCurrent._lblthreaddrug = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Private lblthreadalcohol As Label";
mostCurrent._lblthreadalcohol = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Private lblthreaddiabetes As Label";
mostCurrent._lblthreaddiabetes = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Private lblthreadcardiac As Label";
mostCurrent._lblthreadcardiac = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Private lblthreadmetabolic As Label";
mostCurrent._lblthreadmetabolic = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private lblthreadorgan As Label";
mostCurrent._lblthreadorgan = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private lblthreadmedical As Label";
mostCurrent._vvvvvvv7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
return "";
}
public static String  _hometime_tick() throws Exception{
 //BA.debugLineNum = 249;BA.debugLine="Sub hometime_Tick";
 //BA.debugLineNum = 250;BA.debugLine="lbltime.Text=global.currentDateTime";
mostCurrent._lbltime.setText((Object)(mostCurrent._vvvvvv4._v7(mostCurrent.activityBA)));
 //BA.debugLineNum = 251;BA.debugLine="End Sub";
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
String _myweight = "";
String _height = "";
String _task = "";
String _medicalhistory = "";
String _image = "";
String _updatedat = "";
String _mycount = "";
 //BA.debugLineNum = 110;BA.debugLine="Sub JobDone(Job As HttpJob)";
 //BA.debugLineNum = 111;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 112;BA.debugLine="Dim u As StringUtils";
_u = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 113;BA.debugLine="Try";
try { //BA.debugLineNum = 114;BA.debugLine="If Job.Success Then";
if (_job._vvvvv2) { 
 //BA.debugLineNum = 115;BA.debugLine="Dim res As String";
_res = "";
 //BA.debugLineNum = 116;BA.debugLine="res = Job.GetString";
_res = _job._vvvv3();
 //BA.debugLineNum = 117;BA.debugLine="Log(\"Response from server: \" & res)";
anywheresoftware.b4a.keywords.Common.Log("Response from server: "+_res);
 //BA.debugLineNum = 118;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 119;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
 //BA.debugLineNum = 120;BA.debugLine="If Job.JobName==home Then";
if ((_job._vvvvv1).equals(_vvvvvvv6)) { 
 //BA.debugLineNum = 121;BA.debugLine="Dim PARAMETERS As List";
_parameters = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 122;BA.debugLine="PARAMETERS = parser.NextArray 'returns a list";
_parameters = _parser.NextArray();
 //BA.debugLineNum = 123;BA.debugLine="For Each rootMap As Map In PARAMETERS";
_rootmap = new anywheresoftware.b4a.objects.collections.Map();
final anywheresoftware.b4a.BA.IterableList group13 = _parameters;
final int groupLen13 = group13.getSize();
for (int index13 = 0;index13 < groupLen13 ;index13++){
_rootmap.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group13.Get(index13)));
 //BA.debugLineNum = 124;BA.debugLine="Dim myuserid As String=rootMap.Get(myid)";
_myuserid = BA.ObjectToString(_rootmap.Get((Object)(_vv0)));
 //BA.debugLineNum = 125;BA.debugLine="Dim firstname As String=rootMap.Get(\"FirstName";
_firstname = BA.ObjectToString(_rootmap.Get((Object)("FirstName")));
 //BA.debugLineNum = 126;BA.debugLine="Dim middlename As String=rootMap.Get(\"MiddleNa";
_middlename = BA.ObjectToString(_rootmap.Get((Object)("MiddleName")));
 //BA.debugLineNum = 127;BA.debugLine="Dim lastname As String =rootMap.Get(\"LastName\"";
_lastname = BA.ObjectToString(_rootmap.Get((Object)("LastName")));
 //BA.debugLineNum = 128;BA.debugLine="Dim age As String=rootMap.Get(\"Age\")";
_age = BA.ObjectToString(_rootmap.Get((Object)("Age")));
 //BA.debugLineNum = 129;BA.debugLine="Dim maritalstatus As String=rootMap.Get(\"marit";
_maritalstatus = BA.ObjectToString(_rootmap.Get((Object)("maritalstatus")));
 //BA.debugLineNum = 130;BA.debugLine="Dim phone As String=rootMap.Get(\"Phone\")";
_phone = BA.ObjectToString(_rootmap.Get((Object)("Phone")));
 //BA.debugLineNum = 131;BA.debugLine="Dim email As String=rootMap.Get(\"Email\")";
_email = BA.ObjectToString(_rootmap.Get((Object)("Email")));
 //BA.debugLineNum = 132;BA.debugLine="Dim username As String=rootMap.Get(\"Username\")";
_username = BA.ObjectToString(_rootmap.Get((Object)("Username")));
 //BA.debugLineNum = 133;BA.debugLine="Dim password As String=rootMap.Get(\"Password\")";
_password = BA.ObjectToString(_rootmap.Get((Object)("Password")));
 //BA.debugLineNum = 134;BA.debugLine="Dim gender As String=rootMap.Get(\"Gender\")";
_gender = BA.ObjectToString(_rootmap.Get((Object)("Gender")));
 //BA.debugLineNum = 135;BA.debugLine="Dim residence As String=rootMap.Get(\"Residence";
_residence = BA.ObjectToString(_rootmap.Get((Object)("Residence")));
 //BA.debugLineNum = 136;BA.debugLine="Dim myweight As String=rootMap.Get(\"weight\")";
_myweight = BA.ObjectToString(_rootmap.Get((Object)("weight")));
 //BA.debugLineNum = 137;BA.debugLine="Dim height As String=rootMap.Get(\"height\")";
_height = BA.ObjectToString(_rootmap.Get((Object)("height")));
 //BA.debugLineNum = 138;BA.debugLine="Dim task As String=rootMap.Get(mytask)";
_task = BA.ObjectToString(_rootmap.Get((Object)(_vvv1)));
 //BA.debugLineNum = 139;BA.debugLine="Dim medicalhistory As String=rootMap.Get(\"medi";
_medicalhistory = BA.ObjectToString(_rootmap.Get((Object)("medicalhistory")));
 //BA.debugLineNum = 140;BA.debugLine="Dim image As String=rootMap.Get(\"Image\")";
_image = BA.ObjectToString(_rootmap.Get((Object)("Image")));
 //BA.debugLineNum = 141;BA.debugLine="Dim updatedat As String=rootMap.Get(\"Updated_a";
_updatedat = BA.ObjectToString(_rootmap.Get((Object)("Updated_at")));
 }
;
 //BA.debugLineNum = 143;BA.debugLine="lblusername.Text=username&\"(\"&entity&\")\"";
mostCurrent._lblusername.setText((Object)(_username+"("+_vv7+")"));
 //BA.debugLineNum = 144;BA.debugLine="lblemail.Text=email";
mostCurrent._lblemail.setText((Object)(_email));
 }else if((_job._vvvvv1).equals(_vvvvvvv0)) { 
 //BA.debugLineNum = 146;BA.debugLine="Dim PARAMETERS As List";
_parameters = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 147;BA.debugLine="PARAMETERS = parser.NextArray 'returns a list";
_parameters = _parser.NextArray();
 //BA.debugLineNum = 148;BA.debugLine="For Each rootMap As Map In PARAMETERS";
_rootmap = new anywheresoftware.b4a.objects.collections.Map();
final anywheresoftware.b4a.BA.IterableList group38 = _parameters;
final int groupLen38 = group38.getSize();
for (int index38 = 0;index38 < groupLen38 ;index38++){
_rootmap.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group38.Get(index38)));
 //BA.debugLineNum = 149;BA.debugLine="Dim mycount As String=rootMap.Get(\"Count\")";
_mycount = BA.ObjectToString(_rootmap.Get((Object)("Count")));
 }
;
 //BA.debugLineNum = 151;BA.debugLine="lblthreadcancer.Text=mycount";
mostCurrent._lblthreadcancer.setText((Object)(_mycount));
 //BA.debugLineNum = 152;BA.debugLine="lblthreadalcohol.Text=mycount";
mostCurrent._lblthreadalcohol.setText((Object)(_mycount));
 //BA.debugLineNum = 153;BA.debugLine="lblthreadcardiac.Text=mycount";
mostCurrent._lblthreadcardiac.setText((Object)(_mycount));
 //BA.debugLineNum = 154;BA.debugLine="lblthreaddiabetes.Text=mycount";
mostCurrent._lblthreaddiabetes.setText((Object)(_mycount));
 //BA.debugLineNum = 155;BA.debugLine="lblthreaddrug.Text=mycount";
mostCurrent._lblthreaddrug.setText((Object)(_mycount));
 //BA.debugLineNum = 156;BA.debugLine="lblthreadmetabolic.Text=mycount";
mostCurrent._lblthreadmetabolic.setText((Object)(_mycount));
 //BA.debugLineNum = 157;BA.debugLine="lblthreadnutrition.Text=mycount";
mostCurrent._lblthreadnutrition.setText((Object)(_mycount));
 //BA.debugLineNum = 158;BA.debugLine="lblthreadorgan.Text=mycount";
mostCurrent._lblthreadorgan.setText((Object)(_mycount));
 //BA.debugLineNum = 159;BA.debugLine="lblthreadphysical.Text=mycount";
mostCurrent._lblthreadphysical.setText((Object)(_mycount));
 //BA.debugLineNum = 160;BA.debugLine="lblthreadweight.Text=mycount";
mostCurrent._lblthreadweight.setText((Object)(_mycount));
 }else if((_job._vvvvv1).equals(_vvvvvvvv1)) { 
 //BA.debugLineNum = 162;BA.debugLine="Dim PARAMETERS As List";
_parameters = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 163;BA.debugLine="PARAMETERS = parser.NextArray 'returns a list";
_parameters = _parser.NextArray();
 //BA.debugLineNum = 164;BA.debugLine="For Each rootMap As Map In PARAMETERS";
_rootmap = new anywheresoftware.b4a.objects.collections.Map();
final anywheresoftware.b4a.BA.IterableList group54 = _parameters;
final int groupLen54 = group54.getSize();
for (int index54 = 0;index54 < groupLen54 ;index54++){
_rootmap.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group54.Get(index54)));
 //BA.debugLineNum = 165;BA.debugLine="Dim mycount As String=rootMap.Get(\"Count\")";
_mycount = BA.ObjectToString(_rootmap.Get((Object)("Count")));
 }
;
 //BA.debugLineNum = 167;BA.debugLine="lblthreadcancer.Text=mycount";
mostCurrent._lblthreadcancer.setText((Object)(_mycount));
 }else if((_job._vvvvv1).equals(_vvvvvvvv2)) { 
 //BA.debugLineNum = 169;BA.debugLine="Dim PARAMETERS As List";
_parameters = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 170;BA.debugLine="PARAMETERS = parser.NextArray 'returns a list";
_parameters = _parser.NextArray();
 //BA.debugLineNum = 171;BA.debugLine="For Each rootMap As Map In PARAMETERS";
_rootmap = new anywheresoftware.b4a.objects.collections.Map();
final anywheresoftware.b4a.BA.IterableList group61 = _parameters;
final int groupLen61 = group61.getSize();
for (int index61 = 0;index61 < groupLen61 ;index61++){
_rootmap.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group61.Get(index61)));
 //BA.debugLineNum = 172;BA.debugLine="Dim mycount As String=rootMap.Get(\"Count\")";
_mycount = BA.ObjectToString(_rootmap.Get((Object)("Count")));
 }
;
 //BA.debugLineNum = 174;BA.debugLine="lblthreadnutrition.Text=mycount";
mostCurrent._lblthreadnutrition.setText((Object)(_mycount));
 }else if((_job._vvvvv1).equals(_vvvvvvvv3)) { 
 //BA.debugLineNum = 176;BA.debugLine="Dim PARAMETERS As List";
_parameters = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 177;BA.debugLine="PARAMETERS = parser.NextArray 'returns a list";
_parameters = _parser.NextArray();
 //BA.debugLineNum = 178;BA.debugLine="For Each rootMap As Map In PARAMETERS";
_rootmap = new anywheresoftware.b4a.objects.collections.Map();
final anywheresoftware.b4a.BA.IterableList group68 = _parameters;
final int groupLen68 = group68.getSize();
for (int index68 = 0;index68 < groupLen68 ;index68++){
_rootmap.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group68.Get(index68)));
 //BA.debugLineNum = 179;BA.debugLine="Dim mycount As String=rootMap.Get(\"Count\")";
_mycount = BA.ObjectToString(_rootmap.Get((Object)("Count")));
 }
;
 //BA.debugLineNum = 181;BA.debugLine="lblthreadweight.Text=mycount";
mostCurrent._lblthreadweight.setText((Object)(_mycount));
 }else if((_job._vvvvv1).equals(_vvvvvvvv4)) { 
 //BA.debugLineNum = 183;BA.debugLine="Dim PARAMETERS As List";
_parameters = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 184;BA.debugLine="PARAMETERS = parser.NextArray 'returns a list";
_parameters = _parser.NextArray();
 //BA.debugLineNum = 185;BA.debugLine="For Each rootMap As Map In PARAMETERS";
_rootmap = new anywheresoftware.b4a.objects.collections.Map();
final anywheresoftware.b4a.BA.IterableList group75 = _parameters;
final int groupLen75 = group75.getSize();
for (int index75 = 0;index75 < groupLen75 ;index75++){
_rootmap.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group75.Get(index75)));
 //BA.debugLineNum = 186;BA.debugLine="Dim mycount As String=rootMap.Get(\"Count\")";
_mycount = BA.ObjectToString(_rootmap.Get((Object)("Count")));
 }
;
 //BA.debugLineNum = 188;BA.debugLine="lblthreadphysical.Text=mycount";
mostCurrent._lblthreadphysical.setText((Object)(_mycount));
 }else if((_job._vvvvv1).equals(_vvvvvvvv5)) { 
 //BA.debugLineNum = 190;BA.debugLine="Dim PARAMETERS As List";
_parameters = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 191;BA.debugLine="PARAMETERS = parser.NextArray 'returns a list";
_parameters = _parser.NextArray();
 //BA.debugLineNum = 192;BA.debugLine="For Each rootMap As Map In PARAMETERS";
_rootmap = new anywheresoftware.b4a.objects.collections.Map();
final anywheresoftware.b4a.BA.IterableList group82 = _parameters;
final int groupLen82 = group82.getSize();
for (int index82 = 0;index82 < groupLen82 ;index82++){
_rootmap.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group82.Get(index82)));
 //BA.debugLineNum = 193;BA.debugLine="Dim mycount As String=rootMap.Get(\"Count\")";
_mycount = BA.ObjectToString(_rootmap.Get((Object)("Count")));
 }
;
 //BA.debugLineNum = 195;BA.debugLine="lblthreaddrug.Text=mycount";
mostCurrent._lblthreaddrug.setText((Object)(_mycount));
 }else if((_job._vvvvv1).equals(_vvvvvvvv6)) { 
 //BA.debugLineNum = 197;BA.debugLine="Dim PARAMETERS As List";
_parameters = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 198;BA.debugLine="PARAMETERS = parser.NextArray 'returns a list";
_parameters = _parser.NextArray();
 //BA.debugLineNum = 199;BA.debugLine="For Each rootMap As Map In PARAMETERS";
_rootmap = new anywheresoftware.b4a.objects.collections.Map();
final anywheresoftware.b4a.BA.IterableList group89 = _parameters;
final int groupLen89 = group89.getSize();
for (int index89 = 0;index89 < groupLen89 ;index89++){
_rootmap.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group89.Get(index89)));
 //BA.debugLineNum = 200;BA.debugLine="Dim mycount As String=rootMap.Get(\"Count\")";
_mycount = BA.ObjectToString(_rootmap.Get((Object)("Count")));
 }
;
 //BA.debugLineNum = 202;BA.debugLine="lblthreadalcohol.Text=mycount";
mostCurrent._lblthreadalcohol.setText((Object)(_mycount));
 }else if((_job._vvvvv1).equals(_vvvvvvvv7)) { 
 //BA.debugLineNum = 204;BA.debugLine="Dim PARAMETERS As List";
_parameters = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 205;BA.debugLine="PARAMETERS = parser.NextArray 'returns a list";
_parameters = _parser.NextArray();
 //BA.debugLineNum = 206;BA.debugLine="For Each rootMap As Map In PARAMETERS";
_rootmap = new anywheresoftware.b4a.objects.collections.Map();
final anywheresoftware.b4a.BA.IterableList group96 = _parameters;
final int groupLen96 = group96.getSize();
for (int index96 = 0;index96 < groupLen96 ;index96++){
_rootmap.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group96.Get(index96)));
 //BA.debugLineNum = 207;BA.debugLine="Dim mycount As String=rootMap.Get(\"Count\")";
_mycount = BA.ObjectToString(_rootmap.Get((Object)("Count")));
 }
;
 //BA.debugLineNum = 209;BA.debugLine="lblthreaddiabetes.Text=mycount";
mostCurrent._lblthreaddiabetes.setText((Object)(_mycount));
 }else if((_job._vvvvv1).equals(_vvvvvvvv0)) { 
 //BA.debugLineNum = 211;BA.debugLine="Dim PARAMETERS As List";
_parameters = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 212;BA.debugLine="PARAMETERS = parser.NextArray 'returns a list";
_parameters = _parser.NextArray();
 //BA.debugLineNum = 213;BA.debugLine="For Each rootMap As Map In PARAMETERS";
_rootmap = new anywheresoftware.b4a.objects.collections.Map();
final anywheresoftware.b4a.BA.IterableList group103 = _parameters;
final int groupLen103 = group103.getSize();
for (int index103 = 0;index103 < groupLen103 ;index103++){
_rootmap.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group103.Get(index103)));
 //BA.debugLineNum = 214;BA.debugLine="Dim mycount As String=rootMap.Get(\"Count\")";
_mycount = BA.ObjectToString(_rootmap.Get((Object)("Count")));
 }
;
 //BA.debugLineNum = 216;BA.debugLine="lblthreadcardiac.Text=mycount";
mostCurrent._lblthreadcardiac.setText((Object)(_mycount));
 }else if((_job._vvvvv1).equals(_vvvvvvvvv1)) { 
 //BA.debugLineNum = 218;BA.debugLine="Dim PARAMETERS As List";
_parameters = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 219;BA.debugLine="PARAMETERS = parser.NextArray 'returns a list";
_parameters = _parser.NextArray();
 //BA.debugLineNum = 220;BA.debugLine="For Each rootMap As Map In PARAMETERS";
_rootmap = new anywheresoftware.b4a.objects.collections.Map();
final anywheresoftware.b4a.BA.IterableList group110 = _parameters;
final int groupLen110 = group110.getSize();
for (int index110 = 0;index110 < groupLen110 ;index110++){
_rootmap.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group110.Get(index110)));
 //BA.debugLineNum = 221;BA.debugLine="Dim mycount As String=rootMap.Get(\"Count\")";
_mycount = BA.ObjectToString(_rootmap.Get((Object)("Count")));
 }
;
 //BA.debugLineNum = 223;BA.debugLine="lblthreadmetabolic.Text=mycount";
mostCurrent._lblthreadmetabolic.setText((Object)(_mycount));
 }else if((_job._vvvvv1).equals(_vvvvvvvvv2)) { 
 //BA.debugLineNum = 225;BA.debugLine="Dim PARAMETERS As List";
_parameters = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 226;BA.debugLine="PARAMETERS = parser.NextArray 'returns a list";
_parameters = _parser.NextArray();
 //BA.debugLineNum = 227;BA.debugLine="For Each rootMap As Map In PARAMETERS";
_rootmap = new anywheresoftware.b4a.objects.collections.Map();
final anywheresoftware.b4a.BA.IterableList group117 = _parameters;
final int groupLen117 = group117.getSize();
for (int index117 = 0;index117 < groupLen117 ;index117++){
_rootmap.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group117.Get(index117)));
 //BA.debugLineNum = 228;BA.debugLine="Dim mycount As String=rootMap.Get(\"Count\")";
_mycount = BA.ObjectToString(_rootmap.Get((Object)("Count")));
 }
;
 //BA.debugLineNum = 230;BA.debugLine="lblthreadorgan.Text=mycount";
mostCurrent._lblthreadorgan.setText((Object)(_mycount));
 };
 }else {
 //BA.debugLineNum = 234;BA.debugLine="ToastMessageShow(\"Failed, make sure you have an";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Failed, make sure you have an internet connection.",anywheresoftware.b4a.keywords.Common.True);
 };
 } 
       catch (Exception e126) {
			processBA.setLastException(e126); //BA.debugLineNum = 237;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.Log(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage());
 };
 //BA.debugLineNum = 239;BA.debugLine="Job.Release";
_job._vvvv0();
 //BA.debugLineNum = 240;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
httputils2service._process_globals();
global._process_globals();
signin._process_globals();
cancercare._process_globals();
login._process_globals();
loginselecto._process_globals();
contactsportal._process_globals();
contactsportalusers._process_globals();
signinspec._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 19;BA.debugLine="Dim tm As Timer";
_v0 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 20;BA.debugLine="Private home=\"HOME\" As String";
_vvvvvvv6 = BA.__b (new byte[] {77,85,103,-66}, 516410);
 //BA.debugLineNum = 21;BA.debugLine="Private thread=\"THREAD\" As String";
_vvvvvvv0 = BA.__b (new byte[] {81,82,68,44,78,73}, 493879);
 //BA.debugLineNum = 22;BA.debugLine="Private cancer=\"CANCER\" As String";
_vvvvvvvv1 = BA.__b (new byte[] {70,90,121,-8,74,94}, 94840);
 //BA.debugLineNum = 23;BA.debugLine="Private nutrition=\"NUTRITION\" As String";
_vvvvvvvv2 = BA.__b (new byte[] {75,76,66,-103,70,90,91,-97,95}, 953319);
 //BA.debugLineNum = 24;BA.debugLine="Private weight=\"WEIGHT\" As String";
_vvvvvvvv3 = BA.__b (new byte[] {82,95,106,2,71,89}, 531208);
 //BA.debugLineNum = 25;BA.debugLine="Private physical=\"PHYSICAL\" As String";
_vvvvvvvv4 = BA.__b (new byte[] {85,83,-25,-7,70,79,-5,-3}, 307981);
 //BA.debugLineNum = 26;BA.debugLine="Private drug=\"DRUG\" As String";
_vvvvvvvv5 = BA.__b (new byte[] {65,72,107,-72}, 537886);
 //BA.debugLineNum = 27;BA.debugLine="Private alcohol=\"ALCOHOL\" As String";
_vvvvvvvv6 = BA.__b (new byte[] {68,86,-32,-46,71,66,-21}, 761194);
 //BA.debugLineNum = 28;BA.debugLine="Private diabetes=\"DIABETES\" As String";
_vvvvvvvv7 = BA.__b (new byte[] {65,82,0,-8,74,88,0,-14}, 134270);
 //BA.debugLineNum = 29;BA.debugLine="Private cardiac=\"CARDIAC\" As String";
_vvvvvvvv0 = BA.__b (new byte[] {70,91,-16,106,70,76,-27}, 758645);
 //BA.debugLineNum = 30;BA.debugLine="Private metabolic=\"METABOLIC\" As String";
_vvvvvvvvv1 = BA.__b (new byte[] {72,94,0,-9,77,67,28,-28,82}, 153896);
 //BA.debugLineNum = 31;BA.debugLine="Private organ=\"ORGAN\" As String";
_vvvvvvvvv2 = BA.__b (new byte[] {74,73,-102,73,65}, 369571);
 //BA.debugLineNum = 32;BA.debugLine="Private status=\"STATUS\" As String";
_vvvvvvv5 = BA.__b (new byte[] {86,79,-22,115,90,95}, 287284);
 //BA.debugLineNum = 33;BA.debugLine="Dim userid As String";
_vv6 = "";
 //BA.debugLineNum = 34;BA.debugLine="Dim entity As String";
_vv7 = "";
 //BA.debugLineNum = 35;BA.debugLine="Dim myid As String";
_vv0 = "";
 //BA.debugLineNum = 36;BA.debugLine="Dim mytask As String";
_vvv1 = "";
 //BA.debugLineNum = 37;BA.debugLine="Dim spec As String";
_vvv2 = "";
 //BA.debugLineNum = 38;BA.debugLine="Dim tableuser As String";
_vvv3 = "";
 //BA.debugLineNum = 39;BA.debugLine="Dim tablespec As String";
_vvv4 = "";
 //BA.debugLineNum = 40;BA.debugLine="End Sub";
return "";
}
public static String  _profile_click() throws Exception{
 //BA.debugLineNum = 271;BA.debugLine="Sub profile_Click";
 //BA.debugLineNum = 272;BA.debugLine="ToastMessageShow(\"This is my profile!\",True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("This is my profile!",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 273;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvv3() throws Exception{
String _querycancer = "";
String _querynutrition = "";
String _queryweight = "";
String _queryphysical = "";
String _querydrug = "";
String _queryalcohol = "";
String _querydiabetes = "";
String _querycardiac = "";
String _querymetabolic = "";
String _queryorgan = "";
String _countqueryuser = "";
 //BA.debugLineNum = 275;BA.debugLine="Sub threadcount    'call this on activity create";
 //BA.debugLineNum = 278;BA.debugLine="If entity=\"user\" Then";
if ((_vv7).equals("user")) { 
 //BA.debugLineNum = 279;BA.debugLine="Dim querycancer=\"SELECT COUNT(*) As Count From s";
_querycancer = "SELECT COUNT(*) As Count From specialist where Specialization='Cancer Care & Prevention'";
 //BA.debugLineNum = 280;BA.debugLine="Dim querynutrition=\"SELECT COUNT(*) As Count Fro";
_querynutrition = "SELECT COUNT(*) As Count From specialist where Specialization='Nutrition Guide'";
 //BA.debugLineNum = 281;BA.debugLine="Dim queryweight=\"SELECT COUNT(*) As Count From s";
_queryweight = "SELECT COUNT(*) As Count From specialist where Specialization='Weight Management'";
 //BA.debugLineNum = 282;BA.debugLine="Dim queryphysical=\"SELECT COUNT(*) As Count From";
_queryphysical = "SELECT COUNT(*) As Count From specialist where Specialization='Physical Activity Guide'";
 //BA.debugLineNum = 283;BA.debugLine="Dim querydrug=\"SELECT COUNT(*) As Count From spe";
_querydrug = "SELECT COUNT(*) As Count From specialist where Specialization='Drug & Substance Abuse Control'";
 //BA.debugLineNum = 284;BA.debugLine="Dim queryalcohol=\"SELECT COUNT(*) As Count From";
_queryalcohol = "SELECT COUNT(*) As Count From specialist where Specialization='Alcohol Intoxication Control'";
 //BA.debugLineNum = 285;BA.debugLine="Dim querydiabetes=\"SELECT COUNT(*) As Count From";
_querydiabetes = "SELECT COUNT(*) As Count From specialist where Specialization='Diabetes Corner'";
 //BA.debugLineNum = 286;BA.debugLine="Dim querycardiac=\"SELECT COUNT(*) As Count From";
_querycardiac = "SELECT COUNT(*) As Count From specialist where Specialization='Cardiovascular Disease Control'";
 //BA.debugLineNum = 287;BA.debugLine="Dim querymetabolic=\"SELECT COUNT(*) As Count Fro";
_querymetabolic = "SELECT COUNT(*) As Count From specialist where Specialization='Inherited Metabolic Disorders'";
 //BA.debugLineNum = 288;BA.debugLine="Dim queryorgan=\"SELECT COUNT(*) As Count From sp";
_queryorgan = "SELECT COUNT(*) As Count From specialist where Specialization='Organ Care'";
 }else if((_vv7).equals("specialist")) { 
 //BA.debugLineNum = 290;BA.debugLine="Dim countqueryuser=\"SELECT COUNT(*) As Count Fro";
_countqueryuser = "SELECT COUNT(*) As Count From user";
 };
 //BA.debugLineNum = 293;BA.debugLine="ExecuteRemoteQuery(countqueryuser,thread)";
_vvvvvvv4(_countqueryuser,_vvvvvvv0);
 //BA.debugLineNum = 294;BA.debugLine="ExecuteRemoteQuery(querycancer,cancer)";
_vvvvvvv4(_querycancer,_vvvvvvvv1);
 //BA.debugLineNum = 295;BA.debugLine="ExecuteRemoteQuery(querynutrition,nutrition)";
_vvvvvvv4(_querynutrition,_vvvvvvvv2);
 //BA.debugLineNum = 296;BA.debugLine="ExecuteRemoteQuery(queryweight,weight)";
_vvvvvvv4(_queryweight,_vvvvvvvv3);
 //BA.debugLineNum = 297;BA.debugLine="ExecuteRemoteQuery(queryphysical,physical)";
_vvvvvvv4(_queryphysical,_vvvvvvvv4);
 //BA.debugLineNum = 298;BA.debugLine="ExecuteRemoteQuery(querydrug,drug)";
_vvvvvvv4(_querydrug,_vvvvvvvv5);
 //BA.debugLineNum = 299;BA.debugLine="ExecuteRemoteQuery(queryalcohol,alcohol)";
_vvvvvvv4(_queryalcohol,_vvvvvvvv6);
 //BA.debugLineNum = 300;BA.debugLine="ExecuteRemoteQuery(querydiabetes,diabetes)";
_vvvvvvv4(_querydiabetes,_vvvvvvvv7);
 //BA.debugLineNum = 301;BA.debugLine="ExecuteRemoteQuery(querycardiac,cardiac)";
_vvvvvvv4(_querycardiac,_vvvvvvvv0);
 //BA.debugLineNum = 302;BA.debugLine="ExecuteRemoteQuery(querymetabolic,metabolic)";
_vvvvvvv4(_querymetabolic,_vvvvvvvvv1);
 //BA.debugLineNum = 303;BA.debugLine="ExecuteRemoteQuery(queryorgan,organ)";
_vvvvvvv4(_queryorgan,_vvvvvvvvv2);
 //BA.debugLineNum = 304;BA.debugLine="End Sub";
return "";
}
}
