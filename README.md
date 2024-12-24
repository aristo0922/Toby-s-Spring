
[들어가며](http://ambitious-recess-219.notion.site)

## [1장. 오브젝트와 의존관계](https://ambitious-recess-219.notion.site/1-14475e05e52a8092ba82fa5444d968e4?pvs=4)
### IoC/DI, 제어의 역전

> 오브젝트의 생명주기와 의존관계에 대한 프로그래밍 모델

- 유연성, 확장성이 뛰어난 코드를 만들게 도와주는 객체지향 설계 원칙과 디자인 패턴의 핵심 원리를 담고 있다.
- 스프링은 IoC/DI 를 프레임워크의 근간으로 삼고 있다.
- 말그대로 메소드나 객체의 호출작업을 개발자가 결정하는 것이 아닌 **외부에서 결정되는 것을 의미한다.**
    - **즉, 스프링 컨테이너가 필요에 따라 개발자 대신 Bean들을 제어 해주는 행위이다.**
- 스프링이 직접 제공하는 모든 기술과 API, 컨테이너는 IoC/DI 방식으로 작성되어있다.

### 서비스 추상화

> 구체적인 기술과 환경에 종속되지 않도록 유연한 추상 계층을 두는 방법
>
- 환경이나 서버, 특정 기술에 종속되지 않고 이식성이 뛰어나며 유연한 어플리케이션을 만들도록 한다.

### AOP

> 애플리케이션 코드에 산재해 나타나는 부가적인 기능을 독립적으로 모듈화하는 프로그래밍 모델
>
- 스프링은 AOP 를 이용해서 다양한 엔터프라이즈 서비스를 적용하고도 깔끔한 코드를 유지하도록 한다.
<br/>
<br/>
<br/>

- [1.1 초난감 DAO](https://ambitious-recess-219.notion.site/1-1-DAO-14475e05e52a8046952bdd81e3f943fc?pvs=4)
- [1.2 DAO 의 분리](https://ambitious-recess-219.notion.site/1-2-DAO-14475e05e52a80268e23c6c3f0b182a6?pvs=4)
- [1.3 DAO 의 확장](https://ambitious-recess-219.notion.site/1-3-DAO-14475e05e52a80299825f11b0a84933c?pvs=4)
- [1.4 제어의 역전 IoC](https://ambitious-recess-219.notion.site/1-4-IoC-14675e05e52a807d8c45f5ce40b86e24?pvs=4)
- [1.5 스프링의 IoC](https://ambitious-recess-219.notion.site/1-5-IoC-14c75e05e52a80208576def853a4cb3e?pvs=4)<br/><br/>

### [2장. 테스트](https://ambitious-recess-219.notion.site/2-15a75e05e52a80b2a653f65034de4819?pvs=4)
- [JUnit Test 클래스 수행 방식](https://ambitious-recess-219.notion.site/JUnit-15a75e05e52a808a912fcf30eed667de?pvs=4)
- [2.4.1 테스트를 위한 애플리케이션 컨텍스트 관리](https://ambitious-recess-219.notion.site/2-4-1-15a75e05e52a8006a10be01e972e8237?pvs=4)
- [2.4.2 DI 와 테스트](https://ambitious-recess-219.notion.site/2-4-2-DI-15a75e05e52a80dcab66d46cd6bfa93a?pvs=4)
- [2.5 학습 테스트로 배우는 스프링](https://ambitious-recess-219.notion.site/2-5-15a75e05e52a804a9c8fc8d2fd5d19be?pvs=4)
  <br/><br/>

## [3장. 템플릿](https://ambitious-recess-219.notion.site/3-15175e05e52a809ebed6d269deca5841?pvs=4)

1장 초난감 DAO 코드에 DI 를 적용해나가는 과정으로 **확장과 변경에 용이하게 대응하도록** 개선

### 개방 폐쇄 원칙 OCP
- 변화의 특성이 다른 부분을 구분해주고 다른 목적과 다른 이유에 의해 다른 시점에 독립적으로 변경될 수 있는 효율적인 구조를 만들어주는 원칙
> - 기능이 다양해지고 확장하려는 성질
> - 고정되고 변하지 않으려는 성질

### 템플릿
바뀌는 성질이 다른 코드 중에서<br/>
- 변경이 거의 일어나지 않으며 **일정한 패턴으로 유지**되는 특성을 가진 부분<br/>
- **자유롭게 변경**되는 성질을 가진 부분으로부터 독립<br/>
  ⇒ 효과적으로 활용할 수 있도록 하는 방법


> - 스프링에 적용된 템플릿 기법
> - 완성도 있는 DAO 코드를 만드는 방법


- [3.1 다시 보는 초난감 DAO](https://ambitious-recess-219.notion.site/3-1-DAO-15175e05e52a80f5913af5a1de62dbcf?pvs=4)
- [3.2 변하는 것과 변하지 않는 것](https://ambitious-recess-219.notion.site/3-2-15175e05e52a8015a4e2c04fffba4069?pvs=4)
- [3.3 JDBC 전략 패턴의 최적화](https://ambitious-recess-219.notion.site/3-3-JDBC-15e75e05e52a801e8138cc3d9dc55ba6?pvs=4)
- [3.4 컨텍스트와 DI](https://ambitious-recess-219.notion.site/3-4-DI-16275e05e52a802dac76f92ba0ca131a?pvs=4)
- [3.5 템플릿과 콜백](https://ambitious-recess-219.notion.site/3-5-16275e05e52a80719c63f30e85ca7528?pvs=4)
- [3.6 스프링의 jdbcTemplate](https://ambitious-recess-219.notion.site/3-6-jdbcTemplate-16575e05e52a80adb11ecf10e4a19edd?pvs=4)

[full verions - notion](https://ambitious-recess-219.notion.site/14475e05e52a80aaa0e4c3ca67ce5f8d?pvs=4)