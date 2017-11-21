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

public class cancercare extends Activity implements B4AActivity{
	public static cancercare mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "nikohealthy.com", "nikohealthy.com.cancercare");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (cancercare).");
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
		activityBA = new BA(this, layout, processBA, "nikohealthy.com", "nikohealthy.com.cancercare");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "nikohealthy.com.cancercare", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (cancercare) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (cancercare) Resume **");
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
		return cancercare.class;
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
        BA.LogInfo("** Activity (cancercare) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (cancercare) Resume **");
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
public static String _vv1 = "";
public static String _vv5 = "";
public static String _vvvvvvvvvvvv1 = "";
public static String _vv6 = "";
public static String _vv7 = "";
public static String _vvv2 = "";
public static String _vvv3 = "";
public static String _vvv4 = "";
public static String _vvvvvvvvvvv0 = "";
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollview1 = null;
public static String _vvvvvvvvvvvv2 = "";
public anywheresoftware.b4a.objects.PanelWrapper _vvvvvvvvvvvv3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtchat = null;
public nikohealthy.com.main _vvvvvv2 = null;
public nikohealthy.com.httputils2service _vvvvvv3 = null;
public nikohealthy.com.global _vvvvvv4 = null;
public nikohealthy.com.signin _vvvvvv5 = null;
public nikohealthy.com.login _vvvvvv7 = null;
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
 //BA.debugLineNum = 35;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 38;BA.debugLine="Activity.LoadLayout(\"cancercare\")";
mostCurrent._activity.LoadLayout("cancercare",mostCurrent.activityBA);
 //BA.debugLineNum = 39;BA.debugLine="ProgressDialogShow(\"Loading, please wait...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Loading, please wait...");
 //BA.debugLineNum = 40;BA.debugLine="Label1.Text=spec";
mostCurrent._label1.setText((Object)(mostCurrent._vvv2));
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 49;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 44;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 45;BA.debugLine="tm.Initialize(\"updatechat\",1000)";
_v0.Initialize(processBA,"updatechat",(long) (1000));
 //BA.debugLineNum = 46;BA.debugLine="tm.Enabled=True";
_v0.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return "";
}
public static String  _btnback_click() throws Exception{
 //BA.debugLineNum = 154;BA.debugLine="Sub btnBack_Click";
 //BA.debugLineNum = 156;BA.debugLine="If entity=\"user\" Then";
if ((mostCurrent._vv7).equals("user")) { 
 //BA.debugLineNum = 157;BA.debugLine="StartActivity(contactsportal)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv1.getObject()));
 }else if((mostCurrent._vv7).equals("specialist")) { 
 //BA.debugLineNum = 159;BA.debugLine="StartActivity(contactsportalusers)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._vvvvvvv2.getObject()));
 };
 //BA.debugLineNum = 161;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 162;BA.debugLine="End Sub";
return "";
}
public static String  _btnsend_click() throws Exception{
String _insquery = "";
 //BA.debugLineNum = 163;BA.debugLine="Sub btnsend_Click";
 //BA.debugLineNum = 165;BA.debugLine="mychat=\"[\"&entity&\" says] \"&txtchat.Text";
mostCurrent._vvvvvvvvvvv0 = "["+mostCurrent._vv7+" says] "+mostCurrent._txtchat.getText();
 //BA.debugLineNum = 166;BA.debugLine="If entity=\"specialist\" And tablespec=\"cancercares";
if ((mostCurrent._vv7).equals("specialist") && (mostCurrent._vvv4).equals("cancercarespec")) { 
 //BA.debugLineNum = 167;BA.debugLine="Dim insquery=\"INSERT INTO `cancercarespec` (`Can";
_insquery = "INSERT INTO `cancercarespec` (`Cancerspec_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vvvvvvvvvvvv1+"', '"+mostCurrent._vv6+"', '"+mostCurrent._vvvvvvvvvvv0+"', 'na', '"+_vv1+"');";
 //BA.debugLineNum = 168;BA.debugLine="ExecuteRemoteQuery(insquery, chats)";
_vvvvvvv4(_insquery,mostCurrent._vvvvvvvvvvvv2);
 }else if((mostCurrent._vv7).equals("user") && (mostCurrent._vvv3).equals("cancercareuser")) { 
 //BA.debugLineNum = 170;BA.debugLine="Dim insquery=\"INSERT INTO `cancercareuser` (`C";
_insquery = "INSERT INTO `cancercareuser` (`Canceruser_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vv6+"', '"+mostCurrent._vv5+"', '"+mostCurrent._vvvvvvvvvvv0+"', 'na', '"+_vv1+"');";
 //BA.debugLineNum = 171;BA.debugLine="ExecuteRemoteQuery(insquery, chats)";
_vvvvvvv4(_insquery,mostCurrent._vvvvvvvvvvvv2);
 }else if((mostCurrent._vv7).equals("specialist") && (mostCurrent._vvv4).equals("weightspec")) { 
 //BA.debugLineNum = 173;BA.debugLine="Dim insquery=\"INSERT INTO `weightspec` (`Weights";
_insquery = "INSERT INTO `weightspec` (`Weightspec_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vvvvvvvvvvvv1+"', '"+mostCurrent._vv6+"', '"+mostCurrent._vvvvvvvvvvv0+"', 'na', '"+_vv1+"');";
 //BA.debugLineNum = 174;BA.debugLine="ExecuteRemoteQuery(insquery, chats)";
_vvvvvvv4(_insquery,mostCurrent._vvvvvvvvvvvv2);
 }else if((mostCurrent._vv7).equals("user") && (mostCurrent._vvv3).equals("weightuser")) { 
 //BA.debugLineNum = 176;BA.debugLine="Dim insquery=\"INSERT INTO `weightuser` (`Weigh";
_insquery = "INSERT INTO `weightuser` (`Weightuser_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vv6+"', '"+mostCurrent._vv5+"', '"+mostCurrent._vvvvvvvvvvv0+"', 'na', '"+_vv1+"');";
 //BA.debugLineNum = 177;BA.debugLine="ExecuteRemoteQuery(insquery, chats)";
_vvvvvvv4(_insquery,mostCurrent._vvvvvvvvvvvv2);
 }else if((mostCurrent._vv7).equals("specialist") && (mostCurrent._vvv4).equals("drugspec")) { 
 //BA.debugLineNum = 179;BA.debugLine="Dim insquery=\"INSERT INTO `drugspec` (`Drugspec_";
_insquery = "INSERT INTO `drugspec` (`Drugspec_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vvvvvvvvvvvv1+"', '"+mostCurrent._vv6+"', '"+mostCurrent._vvvvvvvvvvv0+"', 'na', '"+_vv1+"');";
 //BA.debugLineNum = 180;BA.debugLine="ExecuteRemoteQuery(insquery, chats)";
_vvvvvvv4(_insquery,mostCurrent._vvvvvvvvvvvv2);
 }else if((mostCurrent._vv7).equals("user") && (mostCurrent._vvv3).equals("druguser")) { 
 //BA.debugLineNum = 182;BA.debugLine="Dim insquery=\"INSERT INTO `druguser` (`Druguse";
_insquery = "INSERT INTO `druguser` (`Druguser_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vv6+"', '"+mostCurrent._vv5+"', '"+mostCurrent._vvvvvvvvvvv0+"', 'na', '"+_vv1+"');";
 //BA.debugLineNum = 183;BA.debugLine="ExecuteRemoteQuery(insquery, chats)";
_vvvvvvv4(_insquery,mostCurrent._vvvvvvvvvvvv2);
 }else if((mostCurrent._vv7).equals("specialist") && (mostCurrent._vvv4).equals("alcoholspec")) { 
 //BA.debugLineNum = 185;BA.debugLine="Dim insquery=\"INSERT INTO `alcoholspec` (`Alcoho";
_insquery = "INSERT INTO `alcoholspec` (`Alcoholspec_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vvvvvvvvvvvv1+"', '"+mostCurrent._vv6+"', '"+mostCurrent._vvvvvvvvvvv0+"', 'na', '"+_vv1+"');";
 //BA.debugLineNum = 186;BA.debugLine="ExecuteRemoteQuery(insquery, chats)";
_vvvvvvv4(_insquery,mostCurrent._vvvvvvvvvvvv2);
 }else if((mostCurrent._vv7).equals("user") && (mostCurrent._vvv3).equals("alcoholuser")) { 
 //BA.debugLineNum = 188;BA.debugLine="Dim insquery=\"INSERT INTO `alcoholuser` (`Alco";
_insquery = "INSERT INTO `alcoholuser` (`Alcoholuser_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vv6+"', '"+mostCurrent._vv5+"', '"+mostCurrent._vvvvvvvvvvv0+"', 'na', '"+_vv1+"');";
 //BA.debugLineNum = 189;BA.debugLine="ExecuteRemoteQuery(insquery, chats)";
_vvvvvvv4(_insquery,mostCurrent._vvvvvvvvvvvv2);
 }else if((mostCurrent._vv7).equals("specialist") && (mostCurrent._vvv4).equals("diabetesspec")) { 
 //BA.debugLineNum = 191;BA.debugLine="Dim insquery=\"INSERT INTO `diabetesspec` (`Diabe";
_insquery = "INSERT INTO `diabetesspec` (`Diabetesspec_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vvvvvvvvvvvv1+"', '"+mostCurrent._vv6+"', '"+mostCurrent._vvvvvvvvvvv0+"', 'na', '"+_vv1+"');";
 //BA.debugLineNum = 192;BA.debugLine="ExecuteRemoteQuery(insquery, chats)";
_vvvvvvv4(_insquery,mostCurrent._vvvvvvvvvvvv2);
 }else if((mostCurrent._vv7).equals("user") && (mostCurrent._vvv3).equals("diabetesuser")) { 
 //BA.debugLineNum = 194;BA.debugLine="Dim insquery=\"INSERT INTO `diabetesuser` (`Dia";
_insquery = "INSERT INTO `diabetesuser` (`Diabetesuser_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vv6+"', '"+mostCurrent._vv5+"', '"+mostCurrent._vvvvvvvvvvv0+"', 'na', '"+_vv1+"');";
 //BA.debugLineNum = 195;BA.debugLine="ExecuteRemoteQuery(insquery, chats)";
_vvvvvvv4(_insquery,mostCurrent._vvvvvvvvvvvv2);
 }else if((mostCurrent._vv7).equals("specialist") && (mostCurrent._vvv4).equals("cardiacspec")) { 
 //BA.debugLineNum = 197;BA.debugLine="Dim insquery=\"INSERT INTO `cardiacspec` (`Cardia";
_insquery = "INSERT INTO `cardiacspec` (`Cardiacspec_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vvvvvvvvvvvv1+"', '"+mostCurrent._vv6+"', '"+mostCurrent._vvvvvvvvvvv0+"', 'na', '"+_vv1+"');";
 //BA.debugLineNum = 198;BA.debugLine="ExecuteRemoteQuery(insquery, chats)";
_vvvvvvv4(_insquery,mostCurrent._vvvvvvvvvvvv2);
 }else if((mostCurrent._vv7).equals("user") && (mostCurrent._vvv3).equals("cardiacuser")) { 
 //BA.debugLineNum = 200;BA.debugLine="Dim insquery=\"INSERT INTO `cardiacuser` (`Card";
_insquery = "INSERT INTO `cardiacuser` (`Cardiacuser_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vv6+"', '"+mostCurrent._vv5+"', '"+mostCurrent._vvvvvvvvvvv0+"', 'na', '"+_vv1+"');";
 //BA.debugLineNum = 201;BA.debugLine="ExecuteRemoteQuery(insquery, chats)";
_vvvvvvv4(_insquery,mostCurrent._vvvvvvvvvvvv2);
 }else if((mostCurrent._vv7).equals("user") && (mostCurrent._vvv3).equals("nutritionuser")) { 
 //BA.debugLineNum = 203;BA.debugLine="Dim insquery=\"INSERT INTO `nutritionuser` (`Nutr";
_insquery = "INSERT INTO `nutritionuser` (`Nutritionuser_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vv6+"', '"+mostCurrent._vv5+"', '"+mostCurrent._vvvvvvvvvvv0+"', 'na', '"+_vv1+"');";
 //BA.debugLineNum = 204;BA.debugLine="ExecuteRemoteQuery(insquery, chats)";
_vvvvvvv4(_insquery,mostCurrent._vvvvvvvvvvvv2);
 }else if((mostCurrent._vv7).equals("specialist") && (mostCurrent._vvv4).equals("nutritionspec")) { 
 //BA.debugLineNum = 206;BA.debugLine="Dim insquery=\"INSERT INTO `nutritionspec` (`Nutr";
_insquery = "INSERT INTO `nutritionspec` (`Nutritionspec_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vvvvvvvvvvvv1+"', '"+mostCurrent._vv6+"', '"+mostCurrent._vvvvvvvvvvv0+"', 'na', '"+_vv1+"');";
 //BA.debugLineNum = 207;BA.debugLine="ExecuteRemoteQuery(insquery, chats)";
_vvvvvvv4(_insquery,mostCurrent._vvvvvvvvvvvv2);
 }else if((mostCurrent._vv7).equals("user") && (mostCurrent._vvv3).equals("physicaluser")) { 
 //BA.debugLineNum = 209;BA.debugLine="Dim insquery=\"INSERT INTO `physicaluser` (`Physi";
_insquery = "INSERT INTO `physicaluser` (`Physicaluser_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vv6+"', '"+mostCurrent._vv5+"', '"+mostCurrent._vvvvvvvvvvv0+"', 'na', '"+_vv1+"');";
 //BA.debugLineNum = 210;BA.debugLine="ExecuteRemoteQuery(insquery, chats)";
_vvvvvvv4(_insquery,mostCurrent._vvvvvvvvvvvv2);
 }else if((mostCurrent._vv7).equals("specialist") && (mostCurrent._vvv4).equals("physicalspec")) { 
 //BA.debugLineNum = 212;BA.debugLine="Dim insquery=\"INSERT INTO `physicalspec` (`Physi";
_insquery = "INSERT INTO `physicalspec` (`Physicalspec_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vvvvvvvvvvvv1+"', '"+mostCurrent._vv6+"', '"+mostCurrent._vvvvvvvvvvv0+"', 'na', '"+_vv1+"');";
 //BA.debugLineNum = 213;BA.debugLine="ExecuteRemoteQuery(insquery, chats)";
_vvvvvvv4(_insquery,mostCurrent._vvvvvvvvvvvv2);
 }else if((mostCurrent._vv7).equals("user") && (mostCurrent._vvv3).equals("metabolicuser")) { 
 //BA.debugLineNum = 215;BA.debugLine="Dim insquery=\"INSERT INTO `metabolicuser` (`Meta";
_insquery = "INSERT INTO `metabolicuser` (`Metabolicuser_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vv6+"', '"+mostCurrent._vv5+"', '"+mostCurrent._vvvvvvvvvvv0+"', 'na', '"+_vv1+"');";
 //BA.debugLineNum = 216;BA.debugLine="ExecuteRemoteQuery(insquery, chats)";
_vvvvvvv4(_insquery,mostCurrent._vvvvvvvvvvvv2);
 }else if((mostCurrent._vv7).equals("specialist") && (mostCurrent._vvv4).equals("metabolicspec")) { 
 //BA.debugLineNum = 218;BA.debugLine="Dim insquery=\"INSERT INTO `metabolicspec` (`Meta";
_insquery = "INSERT INTO `metabolicspec` (`Metabolicspec_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vvvvvvvvvvvv1+"', '"+mostCurrent._vv6+"', '"+mostCurrent._vvvvvvvvvvv0+"', 'na', '"+_vv1+"');";
 //BA.debugLineNum = 219;BA.debugLine="ExecuteRemoteQuery(insquery, chats)";
_vvvvvvv4(_insquery,mostCurrent._vvvvvvvvvvvv2);
 }else if((mostCurrent._vv7).equals("user") && (mostCurrent._vvv3).equals("organuser")) { 
 //BA.debugLineNum = 221;BA.debugLine="Dim insquery=\"INSERT INTO `organuser` (`Organuse";
_insquery = "INSERT INTO `organuser` (`Organuser_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vv6+"', '"+mostCurrent._vv5+"', '"+mostCurrent._vvvvvvvvvvv0+"', 'na', '"+_vv1+"');";
 //BA.debugLineNum = 222;BA.debugLine="ExecuteRemoteQuery(insquery, chats)";
_vvvvvvv4(_insquery,mostCurrent._vvvvvvvvvvvv2);
 }else if((mostCurrent._vv7).equals("specialist") && (mostCurrent._vvv4).equals("organspec")) { 
 //BA.debugLineNum = 224;BA.debugLine="Dim insquery=\"INSERT INTO `organspec` (`Organspe";
_insquery = "INSERT INTO `organspec` (`Organspec_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vvvvvvvvvvvv1+"', '"+mostCurrent._vv6+"', '"+mostCurrent._vvvvvvvvvvv0+"', 'na', '"+_vv1+"');";
 //BA.debugLineNum = 225;BA.debugLine="ExecuteRemoteQuery(insquery, chats)";
_vvvvvvv4(_insquery,mostCurrent._vvvvvvvvvvvv2);
 };
 //BA.debugLineNum = 227;BA.debugLine="txtchat.Text=\"\"";
mostCurrent._txtchat.setText((Object)(""));
 //BA.debugLineNum = 228;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvv4(String _query,String _jobname) throws Exception{
nikohealthy.com.httpjob _job = null;
 //BA.debugLineNum = 64;BA.debugLine="Sub ExecuteRemoteQuery(Query As String, JobName As";
 //BA.debugLineNum = 65;BA.debugLine="Dim job As HttpJob";
_job = new nikohealthy.com.httpjob();
 //BA.debugLineNum = 66;BA.debugLine="job.Initialize(JobName, Me)";
_job._initialize(processBA,_jobname,cancercare.getObject());
 //BA.debugLineNum = 67;BA.debugLine="job.PostString(global.API,Query)";
_job._vvvv7(mostCurrent._vvvvvv4._v6,_query);
 //BA.debugLineNum = 68;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Dim myspecid=contactsportal.myspecid As String";
mostCurrent._vv5 = mostCurrent._vvvvvvv1._vv5;
 //BA.debugLineNum = 17;BA.debugLine="Dim myuserid=contactsportalusers.myspecid As Stri";
mostCurrent._vvvvvvvvvvvv1 = mostCurrent._vvvvvvv2._vv5;
 //BA.debugLineNum = 18;BA.debugLine="Dim userid=Main.userid As String";
mostCurrent._vv6 = mostCurrent._vvvvvv2._vv6;
 //BA.debugLineNum = 19;BA.debugLine="Dim entity=Main.entity As String";
mostCurrent._vv7 = mostCurrent._vvvvvv2._vv7;
 //BA.debugLineNum = 20;BA.debugLine="Dim spec=Main.spec As String";
mostCurrent._vvv2 = mostCurrent._vvvvvv2._vvv2;
 //BA.debugLineNum = 21;BA.debugLine="Dim tableuser=Main.tableuser As String";
mostCurrent._vvv3 = mostCurrent._vvvvvv2._vvv3;
 //BA.debugLineNum = 22;BA.debugLine="Dim tablespec=Main.tablespec As String";
mostCurrent._vvv4 = mostCurrent._vvvvvv2._vvv4;
 //BA.debugLineNum = 23;BA.debugLine="Dim mychat As String";
mostCurrent._vvvvvvvvvvv0 = "";
 //BA.debugLineNum = 25;BA.debugLine="Private ScrollView1 As ScrollView";
mostCurrent._scrollview1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private chats=\"CHATS\" As String";
mostCurrent._vvvvvvvvvvvv2 = "CHATS";
 //BA.debugLineNum = 28;BA.debugLine="Dim scvpanel As Panel";
mostCurrent._vvvvvvvvvvvv3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private txtchat As EditText";
mostCurrent._txtchat = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(nikohealthy.com.httpjob _job) throws Exception{
anywheresoftware.b4a.objects.StringUtils _u = null;
String _res = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _parameters = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _userimage = null;
int _paneltop = 0;
int _panelheight = 0;
int _lblthreadstop = 0;
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _m = null;
anywheresoftware.b4a.objects.PanelWrapper _subpanel = null;
anywheresoftware.b4a.objects.LabelWrapper _lblchat = null;
anywheresoftware.b4a.objects.ImageViewWrapper _myimageview = null;
anywheresoftware.b4a.objects.StringUtils _su = null;
String _myuserchat = "";
int _lng = 0;
 //BA.debugLineNum = 70;BA.debugLine="Sub JobDone(Job As HttpJob)";
 //BA.debugLineNum = 71;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 72;BA.debugLine="Dim u As StringUtils";
_u = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 73;BA.debugLine="Try";
try { //BA.debugLineNum = 74;BA.debugLine="If Job.Success Then";
if (_job._vvvvv2) { 
 //BA.debugLineNum = 75;BA.debugLine="Dim res As String";
_res = "";
 //BA.debugLineNum = 76;BA.debugLine="res = Job.GetString";
_res = _job._vvvv3();
 //BA.debugLineNum = 77;BA.debugLine="Log(\"Response from server: \" & res)";
anywheresoftware.b4a.keywords.Common.Log("Response from server: "+_res);
 //BA.debugLineNum = 78;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 79;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
 //BA.debugLineNum = 80;BA.debugLine="If Job.JobName==chats Then";
if ((_job._vvvvv1).equals(mostCurrent._vvvvvvvvvvvv2)) { 
 //BA.debugLineNum = 81;BA.debugLine="Dim PARAMETERS As List";
_parameters = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 82;BA.debugLine="PARAMETERS = parser.NextArray 'returns a list";
_parameters = _parser.NextArray();
 //BA.debugLineNum = 84;BA.debugLine="Dim userimage As Bitmap";
_userimage = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 85;BA.debugLine="Dim PanelTop,PanelHeight,lblthreadsTop As Int";
_paneltop = 0;
_panelheight = 0;
_lblthreadstop = 0;
 //BA.debugLineNum = 87;BA.debugLine="userimage.Initialize(File.DirAssets,\"bell.png\")";
_userimage.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"bell.png");
 //BA.debugLineNum = 88;BA.debugLine="PanelTop=0";
_paneltop = (int) (0);
 //BA.debugLineNum = 89;BA.debugLine="scvpanel=ScrollView1.Panel";
mostCurrent._vvvvvvvvvvvv3 = mostCurrent._scrollview1.getPanel();
 //BA.debugLineNum = 91;BA.debugLine="For i=0 To PARAMETERS.Size-1";
{
final int step18 = 1;
final int limit18 = (int) (_parameters.getSize()-1);
for (_i = (int) (0) ; (step18 > 0 && _i <= limit18) || (step18 < 0 && _i >= limit18); _i = ((int)(0 + _i + step18)) ) {
 //BA.debugLineNum = 92;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 93;BA.debugLine="m = PARAMETERS.Get(i)";
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_parameters.Get(_i)));
 //BA.debugLineNum = 95;BA.debugLine="Dim subpanel As Panel";
_subpanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 96;BA.debugLine="Dim lblchat As Label";
_lblchat = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 97;BA.debugLine="Dim myimageview As ImageView";
_myimageview = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 101;BA.debugLine="subpanel.Initialize(\"subpanel\")";
_subpanel.Initialize(mostCurrent.activityBA,"subpanel");
 //BA.debugLineNum = 104;BA.debugLine="Dim su As StringUtils";
_su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 105;BA.debugLine="Dim myuserchat As String";
_myuserchat = "";
 //BA.debugLineNum = 106;BA.debugLine="myuserchat=m.Get(\"Text\")";
_myuserchat = BA.ObjectToString(_m.Get((Object)("Text")));
 //BA.debugLineNum = 107;BA.debugLine="lblchat.Initialize(\"\")";
_lblchat.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 108;BA.debugLine="Dim lng As Int";
_lng = 0;
 //BA.debugLineNum = 109;BA.debugLine="lng=myuserchat.Length";
_lng = _myuserchat.length();
 //BA.debugLineNum = 110;BA.debugLine="subpanel.AddView(lblchat,5dip,5dip,350dip,-2)";
_subpanel.AddView((android.view.View)(_lblchat.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (350)),(int) (-2));
 //BA.debugLineNum = 111;BA.debugLine="lblchat.TextSize=18";
_lblchat.setTextSize((float) (18));
 //BA.debugLineNum = 112;BA.debugLine="lblchat.Tag=\"lblchat\"";
_lblchat.setTag((Object)("lblchat"));
 //BA.debugLineNum = 113;BA.debugLine="lblchat.Text=myuserchat";
_lblchat.setText((Object)(_myuserchat));
 //BA.debugLineNum = 114;BA.debugLine="lblchat.Color=Colors.ARGB(0,0,0,0)";
_lblchat.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 115;BA.debugLine="lblchat.TextColor=Colors.Black";
_lblchat.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 117;BA.debugLine="If entity=\"user\" Then";
if ((mostCurrent._vv7).equals("user")) { 
 //BA.debugLineNum = 118;BA.debugLine="PanelHeight=5dip+su.MeasureMultilineTextHeig";
_panelheight = (int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))+_su.MeasureMultilineTextHeight((android.widget.TextView)(_lblchat.getObject()),_lblchat.getText()));
 //BA.debugLineNum = 119;BA.debugLine="lblthreadsTop=53dip";
_lblthreadstop = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (53));
 }else if((mostCurrent._vv7).equals("specialist")) { 
 //BA.debugLineNum = 122;BA.debugLine="PanelHeight=5dip+su.MeasureMultilineTextHeig";
_panelheight = (int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))+_su.MeasureMultilineTextHeight((android.widget.TextView)(_lblchat.getObject()),_lblchat.getText()));
 //BA.debugLineNum = 123;BA.debugLine="lblthreadsTop=53dip";
_lblthreadstop = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (53));
 };
 //BA.debugLineNum = 134;BA.debugLine="scvpanel.AddView(subpanel,0,PanelTop,ScrollView";
mostCurrent._vvvvvvvvvvvv3.AddView((android.view.View)(_subpanel.getObject()),(int) (0),_paneltop,mostCurrent._scrollview1.getWidth(),_panelheight);
 //BA.debugLineNum = 137;BA.debugLine="PanelTop=PanelTop+PanelHeight+1dip";
_paneltop = (int) (_paneltop+_panelheight+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)));
 }
};
 //BA.debugLineNum = 140;BA.debugLine="scvpanel.Height=PanelTop";
mostCurrent._vvvvvvvvvvvv3.setHeight(_paneltop);
 };
 }else {
 //BA.debugLineNum = 144;BA.debugLine="ToastMessageShow(\"Failed, make sure you have an";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Failed, make sure you have an internet connection.",anywheresoftware.b4a.keywords.Common.True);
 };
 } 
       catch (Exception e53) {
			processBA.setLastException(e53); //BA.debugLineNum = 149;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.Log(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage());
 };
 //BA.debugLineNum = 151;BA.debugLine="Job.Release";
_job._vvvv0();
 //BA.debugLineNum = 152;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim tm As Timer";
_v0 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 10;BA.debugLine="Dim timestamp As String";
_vv1 = "";
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _updatechat_tick() throws Exception{
String _query = "";
 //BA.debugLineNum = 53;BA.debugLine="Sub updatechat_Tick";
 //BA.debugLineNum = 54;BA.debugLine="timestamp=global.currentDateTime";
_vv1 = mostCurrent._vvvvvv4._v7(mostCurrent.activityBA);
 //BA.debugLineNum = 55;BA.debugLine="If entity=\"specialist\" Then";
if ((mostCurrent._vv7).equals("specialist")) { 
 //BA.debugLineNum = 56;BA.debugLine="Dim query=\"SELECT U.Username,S.Username,C.Spec_i";
_query = "SELECT U.Username,S.Username,C.Spec_id,C.User_id,C.Text,C.Updated_at From user As U,specialist As S,"+mostCurrent._vvv4+" As C WHERE C.User_id=U.User_id And C.Spec_id=S.Spec_id And U.User_id="+mostCurrent._vvvvvvvvvvvv1+" And S.Spec_id="+mostCurrent._vv6+" UNION SELECT U.Username,S.Username,C.Spec_id,C.User_id,C.Text,C.Updated_at From user As U,specialist As S,"+mostCurrent._vvv3+" As C WHERE C.User_id=U.User_id And C.Spec_id=S.Spec_id And U.User_id="+mostCurrent._vvvvvvvvvvvv1+" And S.Spec_id="+mostCurrent._vv6+" ORDER BY Updated_at";
 //BA.debugLineNum = 57;BA.debugLine="ExecuteRemoteQuery(query, chats)";
_vvvvvvv4(_query,mostCurrent._vvvvvvvvvvvv2);
 }else if((mostCurrent._vv7).equals("user")) { 
 //BA.debugLineNum = 59;BA.debugLine="Dim query=\"SELECT U.Username,S.Username,C.Spec_i";
_query = "SELECT U.Username,S.Username,C.Spec_id,C.User_id,C.Text,C.Updated_at From user As U,specialist As S,"+mostCurrent._vvv3+" As C WHERE C.User_id=U.User_id And C.Spec_id=S.Spec_id And U.User_id="+mostCurrent._vv6+" And S.Spec_id="+mostCurrent._vv5+" UNION SELECT U.Username,S.Username,C.Spec_id,C.User_id,C.Text,C.Updated_at From user As U,specialist As S,"+mostCurrent._vvv4+" As C WHERE C.User_id=U.User_id And C.Spec_id=S.Spec_id And U.User_id="+mostCurrent._vv6+" And S.Spec_id="+mostCurrent._vv5+" ORDER BY Updated_at";
 //BA.debugLineNum = 60;BA.debugLine="ExecuteRemoteQuery(query, chats)";
_vvvvvvv4(_query,mostCurrent._vvvvvvvvvvvv2);
 };
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return "";
}
}
