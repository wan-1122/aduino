= 아두이노(레오나르도) 키보드 마우스 제어

client에서 server는 json type으로 명령어를 던지고 aduino가 처리합니다.
 
== 구성 및 동작방식
아두이노 + server(java) + client(java)

client에서 mousemove, click, 입력을 제어함

※ 주의 하드웨어 방식으로 활성화 밖에 안 됨
 
== 제한사항
aduino_server는 1.7에서만 동작합니다.(RXTXcomm.jar 문제)

![image](https://user-images.githubusercontent.com/82796693/115205952-0b4f0a00-a135-11eb-91ee-15e1d6ce0a46.png)
 