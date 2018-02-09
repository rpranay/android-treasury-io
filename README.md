# android-treasury-io
This project contains 2 android applications "FedCash" and "TreasuryServ" which act as client and server respectively.
Request can be made in the FedCash app which creates a Bind Service with the TreasuryServ app using the AIDL configuration.
The TreasuryServ then responds with corresponding request by fetching the results from the "Treasury.io" website.
This project demosntrates the use of BindService and AIDL which can be used to create a client-server mechanism.
