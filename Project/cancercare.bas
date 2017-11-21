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
    Dim tm As Timer
	Dim timestamp As String
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
    Dim myspecid=contactsportal.myspecid As String
	Dim myuserid=contactsportalusers.myspecid As String
	Dim userid=Main.userid As String
	Dim entity=Main.entity As String
	Dim spec=Main.spec As String
	Dim tableuser=Main.tableuser As String
	Dim tablespec=Main.tablespec As String
	Dim mychat As String
	
	Private ScrollView1 As ScrollView
	
	Private chats="CHATS" As String	
	Dim scvpanel As Panel
	Private Label1 As Label
	Private txtchat As EditText
End Sub



Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
     Activity.LoadLayout("cancercare")
	ProgressDialogShow("Loading, please wait...")	
	Label1.Text=spec	
	  
End Sub

Sub Activity_Resume
	tm.Initialize("updatechat",1000)
	tm.Enabled=True
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub updatechat_Tick
	timestamp=global.currentDateTime
 If entity="specialist" Then
		Dim query="SELECT U.Username,S.Username,C.Spec_id,C.User_id,C.Text,C.Updated_at From user As U,specialist As S,"&tablespec&" As C WHERE C.User_id=U.User_id And C.Spec_id=S.Spec_id And U.User_id="&myuserid&" And S.Spec_id="&userid&" UNION SELECT U.Username,S.Username,C.Spec_id,C.User_id,C.Text,C.Updated_at From user As U,specialist As S,"&tableuser&" As C WHERE C.User_id=U.User_id And C.Spec_id=S.Spec_id And U.User_id="&myuserid&" And S.Spec_id="&userid&" ORDER BY Updated_at" As String
  ExecuteRemoteQuery(query, chats)
  Else If entity="user" Then
		Dim query="SELECT U.Username,S.Username,C.Spec_id,C.User_id,C.Text,C.Updated_at From user As U,specialist As S,"&tableuser&" As C WHERE C.User_id=U.User_id And C.Spec_id=S.Spec_id And U.User_id="&userid&" And S.Spec_id="&myspecid&" UNION SELECT U.Username,S.Username,C.Spec_id,C.User_id,C.Text,C.Updated_at From user As U,specialist As S,"&tablespec&" As C WHERE C.User_id=U.User_id And C.Spec_id=S.Spec_id And U.User_id="&userid&" And S.Spec_id="&myspecid&" ORDER BY Updated_at" As String
  ExecuteRemoteQuery(query, chats)
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
			If Job.JobName==chats Then		
				Dim PARAMETERS As List
				PARAMETERS = parser.NextArray 'returns a list with maps							
			
			Dim userimage As Bitmap
			Dim PanelTop,PanelHeight,lblthreadsTop As Int
			
			userimage.Initialize(File.DirAssets,"bell.png")
			PanelTop=0
			scvpanel=ScrollView1.Panel
				
			For i=0 To PARAMETERS.Size-1
			Dim m As Map
					m = PARAMETERS.Get(i)		
			   'fill scroll view 1
			Dim subpanel As Panel
			Dim lblchat As Label			
			Dim myimageview As ImageView			
			
			
			
			subpanel.Initialize("subpanel")			
			
			
			Dim su As StringUtils
			Dim myuserchat As String
			myuserchat=m.Get("Text")
			lblchat.Initialize("")
			Dim lng As Int
			lng=myuserchat.Length
			subpanel.AddView(lblchat,5dip,5dip,350dip,-2)			
			lblchat.TextSize=18
			lblchat.Tag="lblchat"
			lblchat.Text=myuserchat				
			lblchat.Color=Colors.ARGB(0,0,0,0)		
					lblchat.TextColor=Colors.Black
			
					If entity="user" Then
						PanelHeight=5dip+su.MeasureMultilineTextHeight(lblchat,lblchat.Text)
						lblthreadsTop=53dip
						'lblchat for user
					Else If entity="specialist" Then
						PanelHeight=5dip+su.MeasureMultilineTextHeight(lblchat,lblchat.Text)
						lblthreadsTop=53dip
						'lblchat for spec
						'start the job process
					End If
		    
			
			'myimageview.Initialize("")
			'subpanel.AddView(myimageview,5dip,5dip,45dip,45dip)
			'myimageview.Bitmap=userimage
			'myimageview.Gravity=Gravity.FILL
			
			scvpanel.AddView(subpanel,0,PanelTop,ScrollView1.Width,PanelHeight)
			'subpanel.Color=Colors.White
			
			PanelTop=PanelTop+PanelHeight+1dip	
					
			Next
			scvpanel.Height=PanelTop	
	End If
	Else
	'ToastMessageShow("Error: " & Job.ErrorMessage, True)	
		ToastMessageShow("Failed, make sure you have an internet connection.", True)			
	End If
	Catch
	'ToastMessageShow(LastException,True)	
	 'ToastMessageShow("No Threads Found", True)
	 Log(LastException.Message)
	End Try
	Job.Release
End Sub

Sub btnBack_Click
	'back to homescreen
	If entity="user" Then	
	StartActivity(contactsportal)
	Else If entity="specialist" Then
	StartActivity(contactsportalusers)
	End If
	Activity.Finish
End Sub
Sub btnsend_Click
	'insert chat to database
	mychat="["&entity&" says] "&txtchat.Text
	If entity="specialist" And tablespec="cancercarespec" Then
  Dim insquery="INSERT INTO `cancercarespec` (`Cancerspec_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"&myuserid&"', '"&userid&"', '"&mychat&"', 'na', '"&timestamp&"');" As String	  
  ExecuteRemoteQuery(insquery, chats)
	Else If entity="user" And tableuser="cancercareuser" Then
    Dim insquery="INSERT INTO `cancercareuser` (`Canceruser_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"&userid&"', '"&myspecid&"', '"&mychat&"', 'na', '"&timestamp&"');" As String	  
  ExecuteRemoteQuery(insquery, chats)
  Else If entity="specialist" And tablespec="weightspec" Then
  Dim insquery="INSERT INTO `weightspec` (`Weightspec_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"&myuserid&"', '"&userid&"', '"&mychat&"', 'na', '"&timestamp&"');" As String	  
  ExecuteRemoteQuery(insquery, chats)
  Else If entity="user" And tableuser="weightuser" Then
    Dim insquery="INSERT INTO `weightuser` (`Weightuser_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"&userid&"', '"&myspecid&"', '"&mychat&"', 'na', '"&timestamp&"');" As String	  
  ExecuteRemoteQuery(insquery, chats)
  Else If entity="specialist" And tablespec="drugspec" Then
  Dim insquery="INSERT INTO `drugspec` (`Drugspec_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"&myuserid&"', '"&userid&"', '"&mychat&"', 'na', '"&timestamp&"');" As String	  
  ExecuteRemoteQuery(insquery, chats)
  Else If entity="user" And tableuser="druguser" Then
    Dim insquery="INSERT INTO `druguser` (`Druguser_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"&userid&"', '"&myspecid&"', '"&mychat&"', 'na', '"&timestamp&"');" As String	  
  ExecuteRemoteQuery(insquery, chats)
  Else If entity="specialist" And tablespec="alcoholspec" Then
  Dim insquery="INSERT INTO `alcoholspec` (`Alcoholspec_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"&myuserid&"', '"&userid&"', '"&mychat&"', 'na', '"&timestamp&"');" As String	  
  ExecuteRemoteQuery(insquery, chats)
  Else If entity="user" And tableuser="alcoholuser" Then
    Dim insquery="INSERT INTO `alcoholuser` (`Alcoholuser_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"&userid&"', '"&myspecid&"', '"&mychat&"', 'na', '"&timestamp&"');" As String	  
  ExecuteRemoteQuery(insquery, chats)
  Else If entity="specialist" And tablespec="diabetesspec" Then
  Dim insquery="INSERT INTO `diabetesspec` (`Diabetesspec_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"&myuserid&"', '"&userid&"', '"&mychat&"', 'na', '"&timestamp&"');" As String	  
  ExecuteRemoteQuery(insquery, chats)
  Else If entity="user" And tableuser="diabetesuser" Then
    Dim insquery="INSERT INTO `diabetesuser` (`Diabetesuser_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"&userid&"', '"&myspecid&"', '"&mychat&"', 'na', '"&timestamp&"');" As String	  
  ExecuteRemoteQuery(insquery, chats)
  Else If entity="specialist" And tablespec="cardiacspec" Then
  Dim insquery="INSERT INTO `cardiacspec` (`Cardiacspec_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"&myuserid&"', '"&userid&"', '"&mychat&"', 'na', '"&timestamp&"');" As String	  
  ExecuteRemoteQuery(insquery, chats)
  Else If entity="user" And tableuser="cardiacuser" Then
    Dim insquery="INSERT INTO `cardiacuser` (`Cardiacuser_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"&userid&"', '"&myspecid&"', '"&mychat&"', 'na', '"&timestamp&"');" As String	  
  ExecuteRemoteQuery(insquery, chats)
	Else If entity="user" And tableuser="nutritionuser" Then
		Dim insquery="INSERT INTO `nutritionuser` (`Nutritionuser_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"&userid&"', '"&myspecid&"', '"&mychat&"', 'na', '"&timestamp&"');" As String
		ExecuteRemoteQuery(insquery, chats)
	Else If entity="specialist" And tablespec="nutritionspec" Then
		Dim insquery="INSERT INTO `nutritionspec` (`Nutritionspec_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"&myuserid&"', '"&userid&"', '"&mychat&"', 'na', '"&timestamp&"');" As String
		ExecuteRemoteQuery(insquery, chats)
	Else If entity="user" And tableuser="physicaluser" Then
		Dim insquery="INSERT INTO `physicaluser` (`Physicaluser_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"&userid&"', '"&myspecid&"', '"&mychat&"', 'na', '"&timestamp&"');" As String
		ExecuteRemoteQuery(insquery, chats)
	Else If entity="specialist" And tablespec="physicalspec" Then
		Dim insquery="INSERT INTO `physicalspec` (`Physicalspec_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"&myuserid&"', '"&userid&"', '"&mychat&"', 'na', '"&timestamp&"');" As String
		ExecuteRemoteQuery(insquery, chats)
	Else If entity="user" And tableuser="metabolicuser" Then
		Dim insquery="INSERT INTO `metabolicuser` (`Metabolicuser_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"&userid&"', '"&myspecid&"', '"&mychat&"', 'na', '"&timestamp&"');" As String
		ExecuteRemoteQuery(insquery, chats)
	Else If entity="specialist" And tablespec="metabolicspec" Then
		Dim insquery="INSERT INTO `metabolicspec` (`Metabolicspec_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"&myuserid&"', '"&userid&"', '"&mychat&"', 'na', '"&timestamp&"');" As String
		ExecuteRemoteQuery(insquery, chats)
	Else If entity="user" And tableuser="organuser" Then
		Dim insquery="INSERT INTO `organuser` (`Organuser_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"&userid&"', '"&myspecid&"', '"&mychat&"', 'na', '"&timestamp&"');" As String
		ExecuteRemoteQuery(insquery, chats)
	Else If entity="specialist" And tablespec="organspec" Then
		Dim insquery="INSERT INTO `organspec` (`Organspec_id`, `User_id`, `Spec_id`, `Text`, `File`, `Updated_at`) VALUES (NULL, '"&myuserid&"', '"&userid&"', '"&mychat&"', 'na', '"&timestamp&"');" As String
		ExecuteRemoteQuery(insquery, chats)
  End If
  txtchat.Text=""
End Sub