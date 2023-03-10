# 내 손 안의 트레이너, AFITCH (A Fitness Coach) 
<span style="color:color">💪홈트레이닝 도우미 안드로이드 어플💪</span>
<br><br>
### Layout 
![image](https://user-images.githubusercontent.com/65281502/224204951-30f709cd-f4fa-4e71-8161-40b4688d5f14.png)
![image](https://user-images.githubusercontent.com/65281502/224204976-bc72578d-c198-431f-8283-256291ccc063.png)

- 코로나 팬데믹 이후 헬스장 이용이 자유롭지 않은 상황을 고려하여, 집에서 운동을 할 수 있게 하는 관리형 어플 입니다.
    
- **모션 유사도 측정**을 통해 운동 자세의 정확도를 점수로 환산하여, 등수 별로 다른 사람들과 경쟁하며 피드백을 받고 의지를 증가 시키는 시스템입니다. 
    
- 날짜 별로 자신의 **식단**과 운동을 저장하고 관리할 수 있습니다.
<br><br>

### Tech Stack
- **RestAPI** - rest API를 통해 서버와 직관적으로 통신하였으며, swagger 을 통해 서버와의 통신을 미리 테스트해볼 수 있었습니다.
- **Camera2** - 안드로이드 camera2 api 를 통해 운동하는 모습을 녹화하고, 녹화된 영상을 서버로 보낼 수 있도록 하였습니다.
- **TextureView** - 운동하는 모습을 녹화할 때 미리보기 화면을 랜더링하기 위한 기능입니다.

<br><br>
## 시스템 개념도
![시스템개념도](https://github.com/Team-Gonguri/Afitch-BackEnd/blob/develop/project/application/src/main/resources/images/%EC%8B%9C%EC%8A%A4%ED%85%9C%20%EA%B0%9C%EB%85%90%EB%8F%84.png)

## ERD
![ERD](https://github.com/Team-Gonguri/Afitch-BackEnd/blob/develop/project/application/src/main/resources/images/%EC%86%8C%EA%B3%B5%20erd.png)
