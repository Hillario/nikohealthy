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
	Dim myspecid As String
	Private contactsspec="CUSER" As String
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
    
	Private ScrollView1 As ScrollView
	Dim userid=Main.userid As String
	Dim entity=Main.entity As String
	Dim myid=Main.myid As String
	Dim mytask=Main.mytask As String
	Dim myspec=Main.spec As String 
	Dim scvpanel As Panel
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
     Activity.LoadLayout("contactsusers")
	 ProgressDialogShow("Loading users, please wait...")
	 fillscrollview
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub fillscrollview
  Dim query="SELECT * FROM user" As String	
  ExecuteRemoteQuery(query, contactsspec)
  
  

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
			If Job.JobName==contactsspec Then		
				Dim PARAMETERS As List
				PARAMETERS = parser.NextArray 'returns a list with maps
				For Each rootMap As Map In PARAMETERS
				Dim myuserid As String=rootMap.Get("User_id")
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
				Dim task As String=rootMap.Get("occupation")
				Dim medicalhistory As String=rootMap.Get("medicalhistory")
				Dim onlinestatus As String=rootMap.Get("status")
				Dim image As String=rootMap.Get("Image")
				Dim updatedat As String=rootMap.Get("Updated_at")
				Next				
				
			
			Dim userimage As Bitmap
			Dim PanelTop,PanelHeight,lblthreadsTop As Int
			
			userimage.Initialize(File.DirAssets,"defaultimage.png")
			PanelTop=0
			scvpanel=ScrollView1.Panel
				
			For i=0 To PARAMETERS.Size-1
			Dim m As Map
					m = PARAMETERS.Get(i)		
			   'fill scroll view 1
			Dim subpanel As Panel
			Dim lblusername As Label
			Dim lblstatus As Label
			Dim lblemail As  Label
			Dim myimageview As ImageView			
			Dim mybtn As Button
			
			subpanel.Initialize("subpanel")			
			'If i Mod 2=0 Then
			PanelHeight=74dip
			lblthreadsTop=53dip
			'Else
			'PanelHeight=74dip
			'lblthreadsTop=40dip
			'End If
			
			scvpanel.AddView(subpanel,0,PanelTop,ScrollView1.Width,PanelHeight)
			subpanel.Color=Colors.ARGB(0,0,0,0)
			
			Dim myuser As String
					myuser=m.Get("Username")
			
			lblusername.Initialize("")
			subpanel.AddView(lblusername,80dip,5dip,240dip,30dip)
			lblusername.TextSize=18
			lblusername.Tag="lblusername"
			lblusername.Text=myuser
					lblusername.Color=Colors.ARGB(0,0,0,0)
			lblusername.TextColor=Colors.Black
			Dim myemail As String
					myemail=m.Get("Email")
			
			
			lblemail.Initialize("")
			subpanel.AddView(lblemail,80dip,lblthreadsTop,240dip,30dip)
			lblemail.TextSize=16
			lblemail.Tag="lblemail"
		    lblemail.Text=myemail
					lblemail.Color=Colors.ARGB(0,0,0,0)
			lblemail.TextColor=Colors.Blue
			
			myimageview.Initialize("")
			subpanel.AddView(myimageview,5dip,5dip,65dip,65dip)
			myimageview.Bitmap=userimage
			myimageview.Gravity=Gravity.FILL
			'If i Mod 2=0 Then
			
			Dim mystatus As String
					mystatus=m.Get("status")
					
			lblstatus.Initialize("")
			subpanel.AddView(lblstatus,80dip,27dip,240dip,30dip)
			lblstatus.TextSize=14
			lblstatus.Tag="lblstatus"
			lblstatus.Text=mystatus
					lblstatus.Color=Colors.ARGB(0,0,0,0)
			lblstatus.TextColor=Colors.Green
			'End If				
			
			'buttons
			myspecid=m.Get("User_id")
			mybtn.Initialize("mybtn")
			mybtn.Tag=myspecid
			subpanel.AddView(mybtn,250dip,20dip,70dip,40dip)
			mybtn.Text="Chat!"			
						
			PanelTop=PanelTop+PanelHeight+50dip			
			Next
			scvpanel.Height=PanelTop

	End If	
	Else
	'ToastMessageShow("Error: " & Job.ErrorMessage, True)	
		ToastMessageShow("Failed, make sure you have an internet connection.", True)			
	End If
	Catch
	'ToastMessageShow(LastException,True)	
	ToastMessageShow("No Users Found", True)
	End Try
	Job.Release
End Sub

Sub btnBack_Click
    StartActivity(Main)
	Activity.Finish	
End Sub

Sub mybtn_Click
Dim b As Button
b=Sender
myspecid=b.Tag
'open the chat activity
StartActivity(cancercare)
Activity.Finish
End Sub

