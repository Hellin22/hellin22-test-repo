import ws from 'k6/ws';
import { check } from 'k6';

// const NUM_ROOMS = parseInt(__ENV.NUM_ROOMS) || 100;
// const VUS = parseInt(__ENV.VUS) || 50;
const VUS = 25000
const NUM_ROOMS = 20

export let options = {
  vus: VUS,         // 가상 사용자 수
  duration: '30s',  // 테스트 시간
};

export default function () {
  const url = 'ws://stress-test-service:8080/ws';  // WebSocket endpoint
  const res = ws.connect(url, function (socket) {
    let isConnected = false;

  socket.on('open', function () {
    console.log('[open] WebSocket opened');

    // 1. CONNECT 프레임 전송
    socket.send('CONNECT\naccept-version:1.0,1.1,2.0\nheart-beat:10000,10000\n\n\u0000');
    });

    socket.on('message', function (message) {
      // 2. CONNECTED 프레임 수신 → 이후 SUBSCRIBE/SEND 가능
      if (!isConnected && message.includes('CONNECTED')) {
        console.log('[connected] STOMP handshake success');
        isConnected = true;

        // 3. SUBSCRIBE 프레임 -> vus는 NUM_ROOMS의 채팅방을 구독함.
        for (let i = 1; i <= NUM_ROOMS; i++) {
          const subscribeMsg = `SUBSCRIBE\nid:sub-${i}\ndestination:/topic/room.${i}\n\n\0`;
          socket.send(subscribeMsg);
        }
        // // 3. SUBSCRIBE 프레임
        // const subscribeMsg = `SUBSCRIBE\nid:sub-1\ndestination:/topic/room.1\n\n\u0000`;
        // socket.send(subscribeMsg);
        // console.log('[subscribe] Subscribed to /topic/room.1');

        // 4. SEND 프레임 (채팅 메시지 전송) -> 구독한 채팅방에 대해서 채팅 메시지를 1개씩 전송
        for (let i = 1; i <= NUM_ROOMS; i++) {
          const payload = {
            sender: 'user',
            message: `hello room ${i}`,
          };
          const sendMsg = `SEND\ndestination:/app/chat.${i}\ncontent-type:application/json\n\n${JSON.stringify(payload)}\0`;
          socket.send(sendMsg);
        }

        // // 4. SEND 프레임 (채팅 메시지 전송)
        // const payload = JSON.stringify({ sender: 'user', content: 'hello' });
        // const sendMsg = `SEND\ndestination:/app/chat.1\ncontent-type:application/json\n\n${payload}\u0000`;
        // socket.send(sendMsg);
        // console.log('[send] Sent message to /app/chat.1');
      } else {
        console.log('[message] Received:', message);
      }
    });

    socket.on('close', function () {
      console.log('[close] WebSocket closed');
    });

    socket.setTimeout(function () {
      socket.close();
    }, 10000); // 10초 후 종료
  });

  check(res, {
    'WebSocket connected (status 101)': (r) => r && r.status === 101,
  });
}
