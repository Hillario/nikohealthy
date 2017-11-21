Type=Activity
Version=6.5
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Private signupspec="SIGNUPSPEC" As String
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.	
	
	Private ScrollView1 As ScrollView
	
	Private txtfname As EditText
	Private txtmname As EditText
	Private txtlname As EditText
	Private txtage As EditText
	Private txtphone As EditText
	Private txtemail As EditText
	Private txtusername As EditText
	Private txtpassword As EditText
	Private txtconfirmpass As EditText
	Private txtresidence As EditText
	Private txtweight As EditText
	Private txtheight As EditText
	Private txtmedic As EditText
	
	
	Dim fname As String
	Dim mname As String
	Dim lname As String
	Dim age As String
	Dim maritalstatus As String
	Dim phone As String
	Dim email As String
	Dim username As String
	Dim password As String	
	Dim Residence As String 
	Dim weight As String 
	Dim height As String	
	Dim medhistory As String	
	Dim mygender As String
	Dim confirmpassword As String
	Dim spec As String
	
	
	Private RadioButton1 As RadioButton 'single
	Private RadioButton2 As RadioButton 'married  
	Private RadioButton3 As RadioButton 'deceased
	Private RadioButton4 As RadioButton
	Private RadioButton5 As RadioButton
	Private ImageView2 As ImageView
	Private Spinner1 As Spinner
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
     Activity.LoadLayout("signin")
	 ScrollView1.Panel.LoadLayout("registerspec")	
	 RadioButton1.Checked=True
	 RadioButton4.Checked=True
	 fillspinner	 
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub btnBack_Click
	Activity.Finish
End Sub

Sub btnupload_Click
ImageView2.Bitmap=LoadBitmap(File.DirAssets,"defaultimagespec.png")
End Sub

Sub btnsignup_Click
	ProgressDialogShow("Please wait...") 'oouuu young m.a
	fname=txtfname.Text
	lname=txtlname.Text
	mname=txtmname.Text
	age=txtage.Text
	phone=txtphone.Text
	email=txtemail.Text
	username=txtusername.Text
	password=txtpassword.Text
	Residence=txtresidence.Text
	height=txtheight.Text
	weight=txtweight.Text
	spec=Spinner1.SelectedItem
	medhistory=txtmedic.Text
	confirmpassword=txtconfirmpass.Text	
	
	If fname.Trim.Length==0 Then	
	ToastMessageShow("Please enter your First Name",True)		
	Else If mname.Trim.Length==0 Then
	ToastMessageShow("Please enter your Middle Name",True)			
	Else If lname.Trim.Length==0 Then
	ToastMessageShow("Please enter your Last Name",True)		
	Else If age.Trim.Length==0 Then
	ToastMessageShow("Please enter your Age",True)			
	Else If phone.Trim.Length==0 Then
	ToastMessageShow("Please enter your Phone Number",True)			
	Else If email.Trim.Length==0 Then
	ToastMessageShow("Please enter your Email",True)			
	Else If username.Trim.Length==0 Then
	ToastMessageShow("Please enter your Username",True)			
	Else If password.Trim.Length<=6 Then
	ToastMessageShow("Password should be more than 6 characters",True)			
	Else If password <> confirmpassword Then
	ToastMessageShow("Passwords does not match! Try Again",True)			
	Else If Residence.Trim.Length==0 Then
	ToastMessageShow("Please enter your Residence",True)			
	Else If weight.Trim.Length==0 Then
	ToastMessageShow("Please enter your Weight",True)			
	Else If height.Trim.Length==0 Then
	ToastMessageShow("Please enter your Height",True)	
	Else If medhistory.Trim.Length==0 Then
	ToastMessageShow("Please enter your Health Condition Status",True)
	Else
	radiomaritalstatus
	genderfill
	Dim query="INSERT INTO `specialist` (`Spec_id`, `FirstName`, `MiddleName`, `LastName`, `Age`, `maritalstatus`, `Phone`, `Email`, `Username`, `Password`, `Gender`, `Residence`, `weight`, `height`, `Specialization`, `medicalhistory`, `status`, `Image`, `Updated_at`) VALUES (NULL, '"&fname&"', '"&mname&"', '"&lname&"', '"&age&"', '"&maritalstatus&"', '"&phone&"', '"&email&"', '"&username&"', '"&password&"', '"&mygender&"', '"&Residence&"', '"&weight&"', '"&height&"', '"&spec&"', '"&medhistory&"', 'online', 'na', '"&global.currentDateTime&"')" As String	
	ExecuteRemoteQuery(query, signupspec)
	ToastMessageShow("Successfully added "&username&" as Specialist,Now log in as Specialist",True)		
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
	Else
	'ToastMessageShow("Error: " & Job.ErrorMessage, True)	
		ToastMessageShow("Failed, make sure you have an internet connection.", True)	    	
	End If
	Catch
	ToastMessageShow(LastException,True)	
	End Try
	Job.Release
End Sub

Sub radiomaritalstatus
    If RadioButton1.Checked Then
	maritalstatus="Single"
	Else If RadioButton2.Checked Then
	maritalstatus="Married"
	Else If  RadioButton3.Checked Then
	maritalstatus="Deceased"
	End If	
End Sub

Sub genderfill
  If RadioButton4.Checked Then
  mygender="Male"
  Else If RadioButton5.Checked Then
  mygender="Female"
  End If
End Sub

Sub fillspinner

Spinner1.AddAll(Array As String("Nutrition Guide","Weight Management","Physical Activity Guide","Drug & Substance Abuse Control","Alcohol Intoxication Control","Cancer Care & Prevention","Diabetes Corner","Cardiovascular Disease Control","Inherited Metabolic Disorders","Organ Care"))

End Sub