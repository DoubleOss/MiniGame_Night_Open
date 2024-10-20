# 픽셀 네트워크 보드게임 죄수들의 밤

## 📝│개요
> ### 보드게임 마피아류 
>
> ### 제작기간 2024.03 ~ 2024.06.15
> ### 사용기술: Java, Forge Api
> 
> ## 💬│방송 편집 영상
> ![Video Label](http://img.youtube.com/vi/cmZEoh5rb5I/0.jpg)
>
> (https://youtu.be/cmZEoh5rb5I)
>
>
***

## 👨🏻‍💻│기능 구현 목차
###   1. [보드판 HUD 구현](https://github.com/DoubleOss/MiniGame_Night_Open?tab=readme-ov-file#1-%EB%B3%B4%EB%93%9C%ED%8C%90-hud-%EA%B5%AC%ED%98%84-1)
###   2. [OpenGL Rotate Animation](https://github.com/DoubleOss/MiniGame_Night_Open?tab=readme-ov-file#2-opengl-rotate-animation-1)
###   3. [투표 시스템](https://github.com/DoubleOss/MiniGame_Night_Open?tab=readme-ov-file#3-%ED%88%AC%ED%91%9C-%EC%8B%9C%EC%8A%A4%ED%85%9C-1)
###   4. [교도관 투표 발각 로직](https://github.com/DoubleOss/MiniGame_Night_Open?tab=readme-ov-file#4-%EA%B5%90%EB%8F%84%EA%B4%80-%ED%88%AC%ED%91%9C-%EB%B0%9C%EA%B0%81-%EB%A1%9C%EC%A7%81-1)

***

### 1. 보드판 HUD 구현
> * ### 캐릭터 얼굴 텍스쳐 외부 웹 다운로드 과정
> * ### 플레이어 응답, 턴 표시 서버 로직
> * ### YES/NO 이미지 OpenGL Color Black 처리
> ![2024-10-21 07;57;17](https://github.com/user-attachments/assets/d8931835-7ba1-4ea3-a177-eac6b17273bc)
> ## 🔗 코드 링크
> * ### [캐릭터 얼굴 텍스쳐 외부 웹 다운로드 과정](https://github.com/DoubleOss/MiniGame_Night_Open/blob/main/src/main/java/com/doubleos/night/packet/CGangsterPlayerListSet.java#L49)
> * ### [플레이어 응답, 턴 표시 서버측 로직 ](https://github.com/DoubleOss/MiniGame_Night_Open/blob/main/src/main/java/com/doubleos/night/variable/MainGame.java#L511)
> * ### [YES/NO 이미지 Black 처리 ](https://github.com/DoubleOss/MiniGame_Night_Open/blob/main/src/main/java/com/doubleos/night/proxy/ClientProxy.java#L853)

***

### 2. OpenGL Rotate Animation
> * ### 클라이언트 Rotate 로직
> * ### 서버측 질문 카드 뽑기 결과 클라이언트 전송 로직
> * ### 카드 데이터 초기화 로직
> ![2024-10-21 07;42;58](https://github.com/user-attachments/assets/e20751ab-a742-4540-a14e-7b0acead567d)
> ![2024-10-21 08;27;16](https://github.com/user-attachments/assets/b725bc4a-b995-4b44-b9b0-a529f71c078e)
> ## 🔗 코드 링크
> * ### [클라이언트 Rotate 로직](https://github.com/DoubleOss/MiniGame_Night_Open/blob/main/src/main/java/com/doubleos/night/util/Render.java#L70)
> * ### [서버측 질문 카드 뽑기 결과 클라이언트 전송 로직](https://github.com/DoubleOss/MiniGame_Night_Open/blob/main/src/main/java/com/doubleos/night/variable/MainGame.java#L448)
> * ### [카드 데이터 초기화 로직](https://github.com/DoubleOss/MiniGame_Night_Open/blob/main/src/main/java/com/doubleos/night/variable/Variable.java#L103)

***


### 3. 투표 시스템
> * ### 투표 Gui, 결과 HUD
> * ### 서버측 투표 로직
> ![2024-10-21 07;46;02](https://github.com/user-attachments/assets/380c3407-1a82-4fb7-b362-f0080f80b56a)
> ![2024-10-21 07;46;20](https://github.com/user-attachments/assets/4ec5f869-c061-4d28-8bcb-49f45a726e4c)
> ## 🔗 코드 링크
> * ### [투표 GUI](https://github.com/DoubleOss/MiniGame_Night_Open/blob/main/src/main/java/com/doubleos/night/gui/GuiVoting.java#L33)
> * ### [투표 결과 HUD](https://github.com/DoubleOss/MiniGame_Night_Open/blob/main/src/main/java/com/doubleos/night/proxy/ClientProxy.java#L660)
> * ### [Gui 투표 완료 버튼 클릭시 서버측 처리 로직 ](https://github.com/DoubleOss/MiniGame_Night_Open/blob/main/src/main/java/com/doubleos/night/packet/SPacketVotingCount.java#L42)
> * ### [서버측 투표 결과 클라이언트 전송 로직 ](https://github.com/DoubleOss/MiniGame_Night_Open/blob/main/src/main/java/com/doubleos/night/variable/MainGame.java#L778)

***

### 4. 교도관 투표 발각 로직
> * ### 플레이어 선택지 질문 Alpha 애니메이션
> * ### Light Image Rotate Animation
> ![2024-10-21 07;46;50](https://github.com/user-attachments/assets/61e71c54-f3dd-4278-9b98-f01b13c4a6c7)
> ## 🔗 코드 링크
> * ### [애니메이션 로직](https://github.com/DoubleOss/MiniGame_Night_Open/blob/main/src/main/java/com/doubleos/night/proxy/ClientProxy.java#L1111)