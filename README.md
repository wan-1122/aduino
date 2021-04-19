# 아두이노 키보드(레오나르도)
 ahk로 software 방식으로 비활성화로 마우스, 키보드를 제어하였으나
 키보드 보안프로그램이 자꾸 막아서 개발하게 됨.
 아두이노 + server(java) + client(java)
 client에서 mousemove, click, 입력을 제어함(하드웨어 방식으로 활성화 밖에 안 됨)
 
 aduino_server는 1.7에서만 동작합니다.
 - RXTXcomm.jar 문제
 
 client에서 server는 json type으로 명령어를 던지고 aduino가 처리합니다.
  서버->아두이노 지연(50m/s)  
 
 - 2021년 기준-
