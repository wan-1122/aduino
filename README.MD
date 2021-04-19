# 아두이노(레오나르도) 키보드 마우스 제어

client에서 server는 json type으로 명령어를 던지고 aduino가 처리합니다.
 
## 구성 및 동작방식
아두이노 + server(java) + client(java)

client에서 mousemove, click, 입력을 제어함


## 주의사항
aduino_server는 1.7에서만 동작합니다.(RXTXcomm.jar 문제)

aduino_server build path를 아래와 같이 설정 합니다.

![image](https://user-images.githubusercontent.com/82796693/115205952-0b4f0a00-a135-11eb-91ee-15e1d6ce0a46.png)
 

## 실행하기
 1. 아두이노를 연결 후 aduino_keyboard 컴파일 후 업로드(인터넷에서 찾아보세요)
 2. 아두이노 com port 찾을 것!(인터넷에서 찾아보세요)
![image](https://user-images.githubusercontent.com/82796693/115208199-46523d00-a137-11eb-9e0d-1e682d63ce10.png)

 3. 찾은 com port로 아두이노 서버 동작
 
![image](https://user-images.githubusercontent.com/82796693/115208456-8addd880-a137-11eb-96ee-fa2596ffcaaa.png)

 4. aduion_client에서 aduino_config.xml로 아두이노 서버 port 및 command 설정

![image](https://user-images.githubusercontent.com/82796693/115208872-050e5d00-a138-11eb-9a15-c4343e3bff5e.png)


 5. aduion_client 실행


# 작성일자
- 2021.04.19
