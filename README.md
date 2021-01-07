# BitTraversal - in development
<p align="center">
    <a href="https://twitter.com/BitTheByte">
      <img src="https://i.ibb.co/z6tf3Z2/unknown.png" width="700">
    </a>
</p>

# Installation
* Requirements
   * BurpSuite >= 1.7
   * JVM Runtime >= 1.8

* Installation from GitHub
   1) Download the latest release from github https://github.com/BitTheByte/BitTraversal/releases
   2) Using burpsuite navigate to `Extender > Add`
   3) Select the downloaded `.jar` file 

# Core Idea
A Mutator will run against every request seen from burpsuite e.g(proxy, repeater, scanner) generating a number of potential urls each appended with a payload to be passed to Executor and Detector classes to detect if one of the detection techniques was successful

This plugin uses two main techniques to identify directory traversal vulnerabilities
* Detection Methods
  1) Static Detection  
  2) Dynamic Detection   
  
 i) Using predefined payloads specified at [payloads.list](https://github.com/BitTheByte/BitTraversal/blob/master/list/payloads.list) which will be fetched at runtime from GitHub and matched against [regex.list](https://github.com/BitTheByte/BitTraversal/blob/master/list/regex.list)
 
 ii) Still in development. the aim to detect same response requests like `/static/css/main.css/` and `/static/../static/css/main.css` with minimal false postives and also apply similar techniques like the ones found in `CVE-2020-5902`, `CVE-2020-15506`
  
# Papers
https://i.blackhat.com/us-18/Wed-August-8/us-18-Orange-Tsai-Breaking-Parser-Logic-Take-Your-Path-Normalization-Off-And-Pop-0days-Out-2.pdf
