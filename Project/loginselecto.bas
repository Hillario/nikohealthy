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
	Dim userbuttonpressed As Boolean
	Dim specbuttonpressed As Boolean
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
     
	
	Private Spinner1 As Spinner
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
     Activity.LoadLayout("loginselect")
	Spinner1.AddAll(Array As String("User","Specialist"))
End Sub
  
Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub



Sub btnuser_Click
	'user logs in
	userbuttonpressed=True
	specbuttonpressed=False
	If Spinner1.SelectedItem="User" Then
	StartActivity(login)
	Else If Spinner1.SelectedItem="Specialist" Then
		specbuttonpressed=True
		userbuttonpressed=False
		StartActivity(login)
		End If
	Activity.Finish
End Sub
Sub btnspec_Click
	'specialist logs in
	specbuttonpressed=True
	userbuttonpressed=False
	StartActivity(login)
	Activity.Finish
End Sub

'disable the back button
Sub Activity_KeyPress (KeyCode As Int) As Boolean                     
   If KeyCode = KeyCodes.KEYCODE_BACK Then
	  Return True                                           
   Else
      Return False
   End If 
End Sub
