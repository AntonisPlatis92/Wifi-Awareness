<?php
$servername = "mysql.hostinger.gr";
$username = "u148364662_antpl";
$password = "allstars21";
$dbname = "u148364662_ssid";

// Create connection
$conn = new mysqli($servername, $username, $password,$dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
//echo "Connected successfully"."<br>";


//loop through and grab variables off the URL
foreach($_REQUEST as $key => $value)
{
if($key =="ssid"){
$ssid = $value;
}

if($key =="bssid"){
$bssid = $value;
}

if($key =="rssi"){
$rssi = $value;
}
}//for each

$sql = "INSERT INTO ssid_entries(ssid,bssid,rssi)
VALUES ($ssid, $bssid, $rssi)";

if ($conn->query($sql) === TRUE) {
    echo "New record created successfully:" . "<br>";
	echo "SSID: " . $ssid . "<br>" . "BSSID: " . $bssid . "<br>" . "RSSI: " . $rssi; 
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();
?>