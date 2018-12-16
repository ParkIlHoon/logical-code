Chapter01 : Opening of HTTP
=================================================
Media Type
-------------------------------------------------
  - HTTP labels data format label called "MIME" to each object transmitted on Web.
  - MIME is a string label consist of main type and sub type and distinguished by '/'.
  - You can see list of MIME types at [Mozilla MDN](https://developer.mozilla.org/ko/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types).

URI (Uniform Resource Idetifier)
-------------------------------------------------
  - URI identifies resources and assigns specific location.
  - URL (Uniform Resource Locator)
    - It describes concrete location of resource. Then it let know where the resource exists and how to access.
    - Most URL follow standard formats.
      <pre><code>http://logical-code.tistory.com/123
      ------|------------------------|-----
      Scheme    Address of Server     Resource
      </code></pre>
      - Scheme : means protocol to access the resource. Many sites change to 'https' for security from 'http'.
  - URN (Uniform Resource Name)
    - The unique name that is not influenced by location of resource.
    - It works wherever location of resource changed.
    - It does not matter accessed several protocols if resource's name unchanged.
    
HTTP Method
-------------------------------------------------
  - All HTTP Messages have one method.
  - Some methods below used mainly.<br>
  
Method Name|Detail
---------|---------
GET|Send specified resource to client from server.
PUT|Save received data from client as specified name.
DELETE|Delete specified resource from server.
POST|Send data to server gateway application.
HEAD|Send only HTTP Header of response.

Message
--------------------------------------------------
  - Request line shows what to do when message is request and what happened when message is response.
  - Header
    - Each fileds consist of key and value distinguished by ':'.
    - The end of header is indicated by an empty line.
  - Body
    - Request Body carries data to server.
    - Response Body returns data to client.
    - Body can include binary data.(ex. image, video, audio)

TCP/IP
--------------------------------------------------
  - HTTP is an application layer protocol.
  - TCP/IP ensured three things below.
    - Data trasmission without error.
    - Ordered transmission. It means data always arrive in sending order.
    - Unbroken data stream.
  - It is a set of packet switching network protocol layered by TCP and IP.
  - It is lost or broken message that never happened after connection between server and client.
  - Progress of print HTML resource by HTTP.
    1. Extract host name from URL.
    2. Convert host name to IP.
    3. Extract port number from URL.
    4. Connect with server by TCP
    5. Send HTTP request to server.
    6. Receive HTTP response from server.
    7. Close connection and print HTML resource.

Component of Web
---------------------------------------------------
  - Proxy
    - The HTTP middleman located between server and client.
    - Transmits All HTTP request from client to server.
    - Used for security and filters request and response.
  - Cache
    - An HTTP proxy server saves copies of frequent documents through this.
    - Since the Cache saved the document, client can receive copies Cache saved.
    - Client can download faster with Cache than real server.
  - Gateway
    - An special server perform as middleman of other servers.
    - Used for converting HTTP traffic to other protocols.
    - Client does not know it communicated to gateway because gateway handles request as real server that has resources.
  - Tunnel
    - Used for transmitting Not-HTTP raw data by HTTP connections.
