ShardingSphere-jdbc를 통한 샤딩 구현 테스트



[ shard key로 SELECT ]
<img width="1280" height="292" alt="image" src="https://github.com/user-attachments/assets/f3a4b943-619e-424b-ae1c-a8c4b8a905e1" />
<img width="1280" height="292" alt="image" src="https://github.com/user-attachments/assets/d8b1c028-b0f4-413f-85d2-89e72110ce63" />


Logic SQL(실제로 보낸 SQL)과 Actual SQL(ShardingSphere-JDBC가 Logic SQL을 해석하고 재생성하여 실제로 JDBC로 보내는 SQL)이 분리되어 처리되는 걸 확인할 수 있다.

샤드 키 조건에 따라 SQL이 ds0라는 특정 샤드로 라우팅 되어서 데이터를 조회한다.

즉, 샤딩이 정상적으로 적용된 것을 확인할 수 있다.

 <br><br>


[ non shard key로 SELECT ]
<img width="1280" height="293" alt="image" src="https://github.com/user-attachments/assets/f0c96589-31ca-4110-ac34-a6124b19a820" />
<img width="1280" height="161" alt="image" src="https://github.com/user-attachments/assets/d8a2a9ab-6893-4a3a-bc8e-d13af8f94a00" />

마찬가지로 Logic SQL이 Actual SQL로 재생성 되어서 처리된다.
하지만 샤드 키 조건이 없기 때문에, 어떤 데이터베이스에 데이터가 있는지를 알 수 없어 모든 샤드(ds0, ds1, ds2)를 다 조회한다.
이런 경우 불필요한 I/O가 증가할 수 있으므로 샤딩을 적용할 때에는 어떤 컬럼을 샤드 키로 지정할지가 중요하다.

하지만 샤드 키 조건이 없기 때문에, 어떤 데이터베이스에 데이터가 있는지를 알 수 없어 모든 샤드(ds0, ds1, ds2)를 다 조회한다.

이런 경우 불필요한 I/O가 증가할 수 있으므로 샤딩을 적용할 때에는 어떤 컬럼을 샤드 키로 지정할지가 중요하다.
<br><br><br>


[ 느낀점 ]<br>
직접 DataSource를 커스텀하여 샤드 키를 추출하고 동적으로 샤드를 결정하는 방법과, ShardingSphere-JDBC를 활용하여 자동 라우팅으로 처리하는 방법 두 가지를 해보았다.

ShardingSphere를 사용할 경우, 샤드 키 조건이 없더라도 자동으로 라우팅 처리를 해주는 점이 편리했다.

반면, 커스텀 방식에서는 샤드 키가 없는 요청이 들어오면 모든 데이터베이스를 조회해야 하고, 이를 직접 구현하고 관리해야 한다. 이러한 과정은 개발의 복잡도를 높이고, 연결 처리와 에러 대응에 더 많은 시간이 소요될 수 있다.

따라서 샤딩 로직을 안정적이고 효율적으로 구현하려면, 가능하다면 ShardingSphere-JDBC 같은 프레임워크를 활용하는 것이 생산성과 안정성 측면에서 좋을 것 같다고 느꼈다.

