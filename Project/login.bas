Type=Activity
Version=6.5
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen:True
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
Private mylogin="login" As String
	Dim logged As Boolean
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
Private txtusername As EditText
	Private txtpassword As EditText
	Private btnlogin As Button
	Private btnsignup As Button
	Private myusername As String
	Private mypassword As String
		
	
	Dim entity As String
	Dim myid As String
	Dim mytask As String
	Private lblshow As Label
	
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
Activity.LoadLayout("main")

If loginselecto.userbuttonpressed==True Then
entity="user"
myid="User_id"
mytask="occupation"
lblshow.Text="Log in as "&entity
Else If loginselecto.specbuttonpressed==True Then
entity="specialist"
myid="Spec_id"
mytask="Specialization"
lblshow.Text="Log in as "&entity
btnsignup.Visible=False
End If
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub



Sub loginbtn_Click
	'login button is clicked
	'check if the texts in the edit texts is in line with the database data		
	'Gets the username and saves	
	ProgressDialogShow("Please wait...") 'oouuu young m.a
	myusername=txtusername.Text
	mypassword=txtpassword.Text		
	If myusername.Trim.Length==0 Then	
	ToastMessageShow("Please enter username",True)	
	End If
	If mypassword.Trim.Length==0 Then
	ToastMessageShow("Please enter Password",True)
	End If
	If myusername=="admin" And mypassword="qwerty123" Then
		ToastMessageShow("Access to specialists registration granted",True)
	btnsignup.Visible=True		
	Else
	Dim query="SELECT * FROM "&entity&" WHERE Username='"&myusername&"' AND Password='"&mypassword&"'" As String	
	ExecuteRemoteQuery(query, mylogin)
	End If		
	If logged=True Then
	'open the next activity
	Else
	'dont log in
	End If
End Sub

Sub ExecuteRemoteQuery(Query As String, JobName As String)
	Dim job As HttpJob
	job.Initialize(JobName, Me)
	job.PostString(global.API,Query)
End Sub

Sub JobDone(Job As HttpJob)
	ProgressDialogHide	
	Dim u As StringUtils
	Try
	If Job.Success Then	   
		Dim res As String
		res = Job.GetString
		Log("Response from server: " & res)
		Dim parser As JSONParser
		parser.Initialize(res)		
			If Job.JobName==mylogin Then		
				Dim PARAMETERS As List
				PARAMETERS = parser.NextArray 'returns a list with maps
				For Each rootMap As Map In PARAMETERS
				Dim userid As String=rootMap.Get(myid)
				Dim firstname As String=rootMap.Get("FirstName")
				Dim middlename As String=rootMap.Get("MiddleName")
				Dim lastname As String =rootMap.Get("LastName")
				Dim age As String=rootMap.Get("Age")
				Dim maritalstatus As String=rootMap.Get("maritalstatus")
				Dim phone As String=rootMap.Get("Phone")
				Dim email As String=rootMap.Get("Email")
				Dim username As String=rootMap.Get("Username")
				Dim password As String=rootMap.Get("Password")
				Dim gender As String=rootMap.Get("Gender")
				Dim residence As String=rootMap.Get("Residence")
				Dim weight As String=rootMap.Get("weight")
				Dim height As String=rootMap.Get("height")
				Dim task As String=rootMap.Get(mytask)
				Dim medicalhistory As String=rootMap.Get("medicalhistory")
				Dim image As String=rootMap.Get("Image")
				Dim updatedat As String=rootMap.Get("Updated_at")
				Next				
		'ToastMessageShow("Username="&username&","&"Password="&password&","&"User_ID="&userid,True)	
		logged=True		
		File.WriteString(File.DirInternal,"session",u.EncodeUrl(userid,"UTF8")&"")	
			 	File.WriteString(File.DirInternal,"id",Job.GetString)	
		       'start activity for home here
			   StartActivity(Main)
			   Activity.Finish
	End If	
	Else
	'ToastMessageShow("Error: " & Job.ErrorMessage, True)	
		ToastMessageShow("Failed, make sure you have an internet connection.", True)
		logged=False	
	End If
	Catch
	ToastMessageShow("Invalid login credentials",True)
	logged=False
	End Try
	Job.Release
End Sub
Sub signupbtn_Click
   If loginselecto.userbuttonpressed Then   
	StartActivity(signin)
	Else If loginselecto.specbuttonpressed Then
	StartActivity(signinspec)
	End If
End Sub

'disable the back button
Sub Activity_KeyPress (KeyCode As Int) As Boolean                     
   If KeyCode = KeyCodes.KEYCODE_BACK Then                           
      StartActivity(loginselecto) 
	  Activity.Finish 
	  Return True                                           
   Else
      Return False
   End If 
End Sub

Sub btnforgotpass_Click
ProgressDialogShow("Please Wait...")
ToastMessageShow("Password reset information sent to your registered email",True)
End Sub

	
