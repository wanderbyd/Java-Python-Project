package com.example.jpatest;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {

}
/**
Spring Data JPA에서는 인터페이스 내의 메서드 이름을 분석하여 다양한 데이터베이스 조회 및 
조작 메서드를 자동으로 생성합니다. 아래에 Spring Data JPA에서 자동으로 생성되는 일반적인 
메서드 종류를 나열합니다:

save(S entity): 엔터티를 저장하거나 업데이트합니다.
findById(ID id): 주어진 기본 키(ID)로 엔터티를 조회합니다.
findAll(): 모든 엔터티를 검색합니다.
deleteById(ID id): 주어진 기본 키(ID)로 엔터티를 삭제합니다.
count(): 엔터티의 총 수를 반환합니다.
existsById(ID id): 주어진 기본 키(ID)로 엔터티가 존재하는지 확인합니다.

또한 Spring Data JPA는 다음과 같은 규칙을 따르는 메서드를 자동으로 생성합니다:
findBy{PropertyName}: {PropertyName} 필드를 기준으로 엔터티를 조회합니다. 
예를 들어, findByFirstName(String firstName) 메서드는 firstName 필드를 기준으로 엔터티를 조회합니다.

findBy{PropertyName}And{AnotherPropertyName}: 여러 필드를 조합하여 엔터티를 조회합니다. 
예를 들어, findByFirstNameAndLastName(String firstName, String lastName) 메서드는 firstName과 
lastName 필드를 모두 고려하여 엔터티를 조회합니다.

findBy{PropertyName}In: {PropertyName} 필드의 값이 주어진 목록 중 하나와 일치하는 엔터티를 조회합니다. 
예를 들어, findByCityIn(List<String> cities) 메서드는 city 필드 값이 주어진 도시 목록 중 하나와 
일치하는 엔터티를 조회합니다.

이와 같이 Spring Data JPA는 메서드 이름을 분석하여 동적 쿼리를 생성하는 기능을 제공하므로 복잡한 데이터베이스 
조회 작업도 간편하게 처리할 수 있습니다. 프로젝트 요구사항에 따라 메서드를 추가로 정의하여 활용할 수 있습니다.
*/

