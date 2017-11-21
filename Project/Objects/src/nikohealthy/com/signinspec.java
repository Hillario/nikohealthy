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

public class signinspec extends Activity implements B4AActivity{
	public static signinspec mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "nikohealthy.com", "nikohealthy.com.signinspec");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (signinspec).");
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
		activityBA = new BA(this, layout, processBA, "nikohealthy.com", "nikohealthy.com.signinspec");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "nikohealthy.com.signinspec", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (signinspec) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (signinspec) Resume **");
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
		return signinspec.class;
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
        BA.LogInfo("** Activity (signinspec) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (signinspec) Resume **");
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
public static String _vvvvvvvvvvvvv3 = "";
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollview1 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtfname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtlname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtage = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtphone = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtemail = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtusername = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpassword = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtconfirmpass = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtresidence = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtweight = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtheight = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmedic = null;
public static String _vvvvvvvvv0 = "";
public static String _vvvvvvvvvv2 = "";
public static String _vvvvvvvvvv1 = "";
public static String _vvvvvvvvvv3 = "";
public static String _vvvvvvvvvvv5 = "";
public static String _vvvvvvvvvv4 = "";
public static String _vvvvvvvvvv5 = "";
public static String _vvvvv3 = "";
public static String _vvvvv4 = "";
public static String _vvvvvvvvvv6 = "";
public static String _vvvvvvvv3 = "";
public static String _vvvvvvvvvv7 = "";
public static String _vvvvvvvvvvv1 = "";
public static String _vvvvvvvvvvv6 = "";
public static String _vvvvvvvvvvv2 = "";
public static String _vvv2 = "";
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radiobutton1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radiobutton2 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radiobutton3 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radiobutton4 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _radiobutton5 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview2 = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner1 = null;
public nikohealthy.com.main _vvvvvv2 = null;
public nikohealthy.com.httputils2service _vvvvvv3 = null;
public nikohealthy.com.global _vvvvvv4 = null;
public nikohealthy.com.signin _vvvvvv5 = null;
public nikohealthy.com.cancercare _vvvvvv6 = null;
public nikohealthy.com.login _vvvvvv7 = null;
public nikohealthy.com.loginselecto _vvvvvv0 = null;
public nikohealthy.com.contactsportal _vvvvvvv1 = null;
public nikohealthy.com.contactsportalusers _vvvvvvv2 = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 60;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 63;BA.debugLine="Activity.LoadLayout(\"signin\")";
mostCurrent._activity.LoadLayout("signin",mostCurrent.activityBA);
 //BA.debugLineNum = 64;BA.debugLine="ScrollView1.Panel.LoadLayout(\"registerspec\")";
mostCurrent._scrollview1.getPanel().LoadLayout("registerspec",mostCurrent.activityBA);
 //BA.debugLineNum = 65;BA.debugLine="RadioButton1.Checked=True";
mostCurrent._radiobutton1.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 66;BA.debugLine="RadioButton4.Checked=True";
mostCurrent._radiobutton4.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 67;BA.debugLine="fillspinner";
_vvvvvvvvvvvvv2();
 //BA.debugLineNum = 68;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 74;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 70;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static String  _btnback_click() throws Exception{
 //BA.debugLineNum = 78;BA.debugLine="Sub btnBack_Click";
 //BA.debugLineNum = 79;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return "";
}
public static String  _btnsignup_click() throws Exception{
String _query = "";
 //BA.debugLineNum = 86;BA.debugLine="Sub btnsignup_Click";
 //BA.debugLineNum = 87;BA.debugLine="ProgressDialogShow(\"Please wait...\") 'oouuu young";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Please wait...");
 //BA.debugLineNum = 88;BA.debugLine="fname=txtfname.Text";
mostCurrent._vvvvvvvvv0 = mostCurrent._txtfname.getText();
 //BA.debugLineNum = 89;BA.debugLine="lname=txtlname.Text";
mostCurrent._vvvvvvvvvv1 = mostCurrent._txtlname.getText();
 //BA.debugLineNum = 90;BA.debugLine="mname=txtmname.Text";
mostCurrent._vvvvvvvvvv2 = mostCurrent._txtmname.getText();
 //BA.debugLineNum = 91;BA.debugLine="age=txtage.Text";
mostCurrent._vvvvvvvvvv3 = mostCurrent._txtage.getText();
 //BA.debugLineNum = 92;BA.debugLine="phone=txtphone.Text";
mostCurrent._vvvvvvvvvv4 = mostCurrent._txtphone.getText();
 //BA.debugLineNum = 93;BA.debugLine="email=txtemail.Text";
mostCurrent._vvvvvvvvvv5 = mostCurrent._txtemail.getText();
 //BA.debugLineNum = 94;BA.debugLine="username=txtusername.Text";
mostCurrent._vvvvv3 = mostCurrent._txtusername.getText();
 //BA.debugLineNum = 95;BA.debugLine="password=txtpassword.Text";
mostCurrent._vvvvv4 = mostCurrent._txtpassword.getText();
 //BA.debugLineNum = 96;BA.debugLine="Residence=txtresidence.Text";
mostCurrent._vvvvvvvvvv6 = mostCurrent._txtresidence.getText();
 //BA.debugLineNum = 97;BA.debugLine="height=txtheight.Text";
mostCurrent._vvvvvvvvvv7 = mostCurrent._txtheight.getText();
 //BA.debugLineNum = 98;BA.debugLine="weight=txtweight.Text";
mostCurrent._vvvvvvvv3 = mostCurrent._txtweight.getText();
 //BA.debugLineNum = 99;BA.debugLine="spec=Spinner1.SelectedItem";
mostCurrent._vvv2 = mostCurrent._spinner1.getSelectedItem();
 //BA.debugLineNum = 100;BA.debugLine="medhistory=txtmedic.Text";
mostCurrent._vvvvvvvvvvv1 = mostCurrent._txtmedic.getText();
 //BA.debugLineNum = 101;BA.debugLine="confirmpassword=txtconfirmpass.Text";
mostCurrent._vvvvvvvvvvv2 = mostCurrent._txtconfirmpass.getText();
 //BA.debugLineNum = 103;BA.debugLine="If fname.Trim.Length==0 Then";
if (mostCurrent._vvvvvvvvv0.trim().length()==0) { 
 //BA.debugLineNum = 104;BA.debugLine="ToastMessageShow(\"Please enter your First Name\",T";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Please enter your First Name",anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._vvvvvvvvvv2.trim().length()==0) { 
 //BA.debugLineNum = 106;BA.debugLine="ToastMessageShow(\"Please enter your Middle Name\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Please enter your Middle Name",anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._vvvvvvvvvv1.trim().length()==0) { 
 //BA.debugLineNum = 108;BA.debugLine="ToastMessageShow(\"Please enter your Last Name\",Tr";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Please enter your Last Name",anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._vvvvvvvvvv3.trim().length()==0) { 
 //BA.debugLineNum = 110;BA.debugLine="ToastMessageShow(\"Please enter your Age\",True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Please enter your Age",anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._vvvvvvvvvv4.trim().length()==0) { 
 //BA.debugLineNum = 112;BA.debugLine="ToastMessageShow(\"Please enter your Phone Number\"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Please enter your Phone Number",anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._vvvvvvvvvv5.trim().length()==0) { 
 //BA.debugLineNum = 114;BA.debugLine="ToastMessageShow(\"Please enter your Email\",True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Please enter your Email",anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._vvvvv3.trim().length()==0) { 
 //BA.debugLineNum = 116;BA.debugLine="ToastMessageShow(\"Please enter your Username\",Tru";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Please enter your Username",anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._vvvvv4.trim().length()<=6) { 
 //BA.debugLineNum = 118;BA.debugLine="ToastMessageShow(\"Password should be more than 6";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Password should be more than 6 characters",anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._vvvvv4).equals(mostCurrent._vvvvvvvvvvv2) == false) { 
 //BA.debugLineNum = 120;BA.debugLine="ToastMessageShow(\"Passwords does not match! Try A";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Passwords does not match! Try Again",anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._vvvvvvvvvv6.trim().length()==0) { 
 //BA.debugLineNum = 122;BA.debugLine="ToastMessageShow(\"Please enter your Residence\",Tr";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Please enter your Residence",anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._vvvvvvvv3.trim().length()==0) { 
 //BA.debugLineNum = 124;BA.debugLine="ToastMessageShow(\"Please enter your Weight\",True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Please enter your Weight",anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._vvvvvvvvvv7.trim().length()==0) { 
 //BA.debugLineNum = 126;BA.debugLine="ToastMessageShow(\"Please enter your Height\",True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Please enter your Height",anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._vvvvvvvvvvv1.trim().length()==0) { 
 //BA.debugLineNum = 128;BA.debugLine="ToastMessageShow(\"Please enter your Health Condit";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Please enter your Health Condition Status",anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 130;BA.debugLine="radiomaritalstatus";
_vvvvvvvvvvv3();
 //BA.debugLineNum = 131;BA.debugLine="genderfill";
_vvvvvvvvvvv4();
 //BA.debugLineNum = 132;BA.debugLine="Dim query=\"INSERT INTO `specialist` (`Spec_id`, `";
_query = "INSERT INTO `specialist` (`Spec_id`, `FirstName`, `MiddleName`, `LastName`, `Age`, `maritalstatus`, `Phone`, `Email`, `Username`, `Password`, `Gender`, `Residence`, `weight`, `height`, `Specialization`, `medicalhistory`, `status`, `Image`, `Updated_at`) VALUES (NULL, '"+mostCurrent._vvvvvvvvv0+"', '"+mostCurrent._vvvvvvvvvv2+"', '"+mostCurrent._vvvvvvvvvv1+"', '"+mostCurrent._vvvvvvvvvv3+"', '"+mostCurrent._vvvvvvvvvvv5+"', '"+mostCurrent._vvvvvvvvvv4+"', '"+mostCurrent._vvvvvvvvvv5+"', '"+mostCurrent._vvvvv3+"', '"+mostCurrent._vvvvv4+"', '"+mostCurrent._vvvvvvvvvvv6+"', '"+mostCurrent._vvvvvvvvvv6+"', '"+mostCurrent._vvvvvvvv3+"', '"+mostCurrent._vvvvvvvvvv7+"', '"+mostCurrent._vvv2+"', '"+mostCurrent._vvvvvvvvvvv1+"', 'online', 'na', '"+mostCurrent._vvvvvv4._v7(mostCurrent.activityBA)+"')";
 //BA.debugLineNum = 133;BA.debugLine="ExecuteRemoteQuery(query, signupspec)";
_vvvvvvv4(_query,_vvvvvvvvvvvvv3);
 //BA.debugLineNum = 134;BA.debugLine="ToastMessageShow(\"Successfully added \"&username&\"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Successfully added "+mostCurrent._vvvvv3+" as Specialist,Now log in as Specialist",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 136;BA.debugLine="End Sub";
return "";
}
public static String  _btnupload_click() throws Exception{
 //BA.debugLineNum = 82;BA.debugLine="Sub btnupload_Click";
 //BA.debugLineNum = 83;BA.debugLine="ImageView2.Bitmap=LoadBitmap(File.DirAssets,\"defau";
mostCurrent._imageview2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"defaultimagespec.png").getObject()));
 //BA.debugLineNum = 84;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvv4(String _query,String _jobname) throws Exception{
nikohealthy.com.httpjob _job = null;
 //BA.debugLineNum = 138;BA.debugLine="Sub ExecuteRemoteQuery(Query As String, JobName As";
 //BA.debugLineNum = 139;BA.debugLine="Dim job As HttpJob";
_job = new nikohealthy.com.httpjob();
 //BA.debugLineNum = 140;BA.debugLine="job.Initialize(JobName, Me)";
_job._initialize(processBA,_jobname,signinspec.getObject());
 //BA.debugLineNum = 141;BA.debugLine="job.PostString(global.API,Query)";
_job._vvvv7(mostCurrent._vvvvvv4._v6,_query);
 //BA.debugLineNum = 142;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvvvv2() throws Exception{
 //BA.debugLineNum = 182;BA.debugLine="Sub fillspinner";
 //BA.debugLineNum = 184;BA.debugLine="Spinner1.AddAll(Array As String(\"Nutrition Guide\",";
mostCurrent._spinner1.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"Nutrition Guide","Weight Management","Physical Activity Guide","Drug & Substance Abuse Control","Alcohol Intoxication Control","Cancer Care & Prevention","Diabetes Corner","Cardiovascular Disease Control","Inherited Metabolic Disorders","Organ Care"}));
 //BA.debugLineNum = 186;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvv4() throws Exception{
 //BA.debugLineNum = 174;BA.debugLine="Sub genderfill";
 //BA.debugLineNum = 175;BA.debugLine="If RadioButton4.Checked Then";
if (mostCurrent._radiobutton4.getChecked()) { 
 //BA.debugLineNum = 176;BA.debugLine="mygender=\"Male\"";
mostCurrent._vvvvvvvvvvv6 = "Male";
 }else if(mostCurrent._radiobutton5.getChecked()) { 
 //BA.debugLineNum = 178;BA.debugLine="mygender=\"Female\"";
mostCurrent._vvvvvvvvvvv6 = "Female";
 };
 //BA.debugLineNum = 180;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private ScrollView1 As ScrollView";
mostCurrent._scrollview1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private txtfname As EditText";
mostCurrent._txtfname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private txtmname As EditText";
mostCurrent._txtmname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private txtlname As EditText";
mostCurrent._txtlname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private txtage As EditText";
mostCurrent._txtage = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private txtphone As EditText";
mostCurrent._txtphone = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private txtemail As EditText";
mostCurrent._txtemail = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private txtusername As EditText";
mostCurrent._txtusername = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private txtpassword As EditText";
mostCurrent._txtpassword = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private txtconfirmpass As EditText";
mostCurrent._txtconfirmpass = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private txtresidence As EditText";
mostCurrent._txtresidence = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private txtweight As EditText";
mostCurrent._txtweight = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private txtheight As EditText";
mostCurrent._txtheight = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private txtmedic As EditText";
mostCurrent._txtmedic = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim fname As String";
mostCurrent._vvvvvvvvv0 = "";
 //BA.debugLineNum = 34;BA.debugLine="Dim mname As String";
mostCurrent._vvvvvvvvvv2 = "";
 //BA.debugLineNum = 35;BA.debugLine="Dim lname As String";
mostCurrent._vvvvvvvvvv1 = "";
 //BA.debugLineNum = 36;BA.debugLine="Dim age As String";
mostCurrent._vvvvvvvvvv3 = "";
 //BA.debugLineNum = 37;BA.debugLine="Dim maritalstatus As String";
mostCurrent._vvvvvvvvvvv5 = "";
 //BA.debugLineNum = 38;BA.debugLine="Dim phone As String";
mostCurrent._vvvvvvvvvv4 = "";
 //BA.debugLineNum = 39;BA.debugLine="Dim email As String";
mostCurrent._vvvvvvvvvv5 = "";
 //BA.debugLineNum = 40;BA.debugLine="Dim username As String";
mostCurrent._vvvvv3 = "";
 //BA.debugLineNum = 41;BA.debugLine="Dim password As String";
mostCurrent._vvvvv4 = "";
 //BA.debugLineNum = 42;BA.debugLine="Dim Residence As String";
mostCurrent._vvvvvvvvvv6 = "";
 //BA.debugLineNum = 43;BA.debugLine="Dim weight As String";
mostCurrent._vvvvvvvv3 = "";
 //BA.debugLineNum = 44;BA.debugLine="Dim height As String";
mostCurrent._vvvvvvvvvv7 = "";
 //BA.debugLineNum = 45;BA.debugLine="Dim medhistory As String";
mostCurrent._vvvvvvvvvvv1 = "";
 //BA.debugLineNum = 46;BA.debugLine="Dim mygender As String";
mostCurrent._vvvvvvvvvvv6 = "";
 //BA.debugLineNum = 47;BA.debugLine="Dim confirmpassword As String";
mostCurrent._vvvvvvvvvvv2 = "";
 //BA.debugLineNum = 48;BA.debugLine="Dim spec As String";
mostCurrent._vvv2 = "";
 //BA.debugLineNum = 51;BA.debugLine="Private RadioButton1 As RadioButton 'single";
mostCurrent._radiobutton1 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private RadioButton2 As RadioButton 'married";
mostCurrent._radiobutton2 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private RadioButton3 As RadioButton 'deceased";
mostCurrent._radiobutton3 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private RadioButton4 As RadioButton";
mostCurrent._radiobutton4 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private RadioButton5 As RadioButton";
mostCurrent._radiobutton5 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private ImageView2 As ImageView";
mostCurrent._imageview2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Private Spinner1 As Spinner";
mostCurrent._spinner1 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 58;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(nikohealthy.com.httpjob _job) throws Exception{
anywheresoftware.b4a.objects.StringUtils _u = null;
String _res = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
 //BA.debugLineNum = 144;BA.debugLine="Sub JobDone(Job As HttpJob)";
 //BA.debugLineNum = 145;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 146;BA.debugLine="Dim u As StringUtils";
_u = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 147;BA.debugLine="Try";
try { //BA.debugLineNum = 148;BA.debugLine="If Job.Success Then";
if (_job._vvvvv2) { 
 //BA.debugLineNum = 149;BA.debugLine="Dim res As String";
_res = "";
 //BA.debugLineNum = 150;BA.debugLine="res = Job.GetString";
_res = _job._vvvv3();
 //BA.debugLineNum = 151;BA.debugLine="Log(\"Response from server: \" & res)";
anywheresoftware.b4a.keywords.Common.Log("Response from server: "+_res);
 //BA.debugLineNum = 152;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 153;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
 }else {
 //BA.debugLineNum = 156;BA.debugLine="ToastMessageShow(\"Failed, make sure you have an";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Failed, make sure you have an internet connection.",anywheresoftware.b4a.keywords.Common.True);
 };
 } 
       catch (Exception e14) {
			processBA.setLastException(e14); //BA.debugLineNum = 159;BA.debugLine="ToastMessageShow(LastException,True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 161;BA.debugLine="Job.Release";
_job._vvvv0();
 //BA.debugLineNum = 162;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Private signupspec=\"SIGNUPSPEC\" As String";
_vvvvvvvvvvvvv3 = BA.__b (new byte[] {86,83,-48,103,90,93,-64,98,84,64}, 724601);
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _vvvvvvvvvvv3() throws Exception{
 //BA.debugLineNum = 164;BA.debugLine="Sub radiomaritalstatus";
 //BA.debugLineNum = 165;BA.debugLine="If RadioButton1.Checked Then";
if (mostCurrent._radiobutton1.getChecked()) { 
 //BA.debugLineNum = 166;BA.debugLine="maritalstatus=\"Single\"";
mostCurrent._vvvvvvvvvvv5 = "Single";
 }else if(mostCurrent._radiobutton2.getChecked()) { 
 //BA.debugLineNum = 168;BA.debugLine="maritalstatus=\"Married\"";
mostCurrent._vvvvvvvvvvv5 = "Married";
 }else if(mostCurrent._radiobutton3.getChecked()) { 
 //BA.debugLineNum = 170;BA.debugLine="maritalstatus=\"Deceased\"";
mostCurrent._vvvvvvvvvvv5 = "Deceased";
 };
 //BA.debugLineNum = 172;BA.debugLine="End Sub";
return "";
}
}
