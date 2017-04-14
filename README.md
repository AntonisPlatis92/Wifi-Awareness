# Wifi-Awareness
An Android App that scans Wifi Connections, prints the results or sends them to a Database

In the MainActivity of the app there are two 2 functional buttons that trigger the following activities:

-ScanAndPrintActivity, that scans nearby Wifi Access Points and prints their SSID along with their corresponding BSSID (MAC Address) and RSSI (Signal Strenght)

-ScanAndSendActivity, that scans nearby Access Points and sends the 2 APs with the best Signal to a Database through HTTP Requests and parsing parameters (Sends the SSID along with their corresponding BSSID (MAC Address) and RSSI (Signal Strenght) )

The HTTP Request is executing a PHP Script on a free server i own that sipmply adds a new entry on my Database (SSID, BSSID, RSSI).

You will find all the .java files for all the Activities along with their corresponding xml layout files and the Manifest file
