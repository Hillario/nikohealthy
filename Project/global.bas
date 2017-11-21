Type=StaticCode
Version=6.5
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
 'Dim API As String="http://192.168.137.1/NikoHealthy/login.php"
 Dim API As String="http://cardiaccenter.co.ke/nikohealthy/nklogin.php"
End Sub

Sub currentDateTime As String

 Dim today As Long
 Dim year As Int
 Dim month As Int
 Dim day As Int
 Dim hour As Int
 Dim minute As Int
 Dim second As Int
 
 today=DateTime.Now
 
 year=DateTime.GetYear(today)
 month=DateTime.GetMonth(today)
 day=DateTime.GetDayOfMonth(today)
 hour=DateTime.GetHour(today)
 minute=DateTime.GetMinute(today)
 second=DateTime.GetSecond(today)
 
 Dim timestamp As String
 
 timestamp=year&"-"&month&"-"&day&" "&hour&":"&minute&":"&second
 Return timestamp
End Sub