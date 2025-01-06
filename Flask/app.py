# from flask import Flask, request, jsonify
# from model import user_profile, run_model

# app = Flask(__name__)

# @app.route('/receiveUser', methods=['POST'])
# def receive_user():
    
#     try:
#         user_data = request.get_json()  # POST 요청 데이터 받기
#         print("받은 사용자 데이터:", user_data)  # 사용자 데이터 출력
        
#         # 사용자 데이터를 `user_profile`에 저장
#         user_profile['name'] = user_data.get('name', '이름 없음')
#         user_profile['preferred_genres'] = user_data.get('preferred_genres', ['없음'])

#         # 모델 수행
#         model_result = run_model(user_profile)
#         print("모델 결과:", model_result)

#         # 결과 반환 (추천 책 제목 및 사용자 ID)
#         response = {
#             'bookTitle': model_result['recommendation'],  # 추천된 책 제목
#             'userId': user_profile['name']                # 사용자 ID
#         }
#         return jsonify(response)
#     except Exception as e:
#         print("에러 발생:", e)
#         return jsonify({'error': str(e)}), 500
# # @app.route('/')
# # def index():
# #     return "Flask 서버가 정상적으로 실행 중입니다!"
# if __name__ == '__main__':
#     app.run(debug=True)

# from flask import Flask, request, jsonify
# import json
# import subprocess

# app = Flask(__name__)

# @app.route('/user_info', methods=['POST'])
# def receive_user_info():
#     user_info = request.get_json()
#     print("Received user info:", user_info)
    
#     # 사용자 정보를 user_profile.json 파일에 저장
#     with open('user_profile.json', 'w', encoding='utf-8') as f:
#         json.dump(user_info, f, ensure_ascii=False, indent=4)
    
#     # model.py 실행
#     try:
#         subprocess.run(['python', 'model.py'], check=True)
#     except subprocess.CalledProcessError as e:
#         print("Error executing model.py:", e)
    
#     return jsonify({'status': 'success', 'message': 'User info received and model.py executed'}), 200

# if __name__ == '__main__':
#     app.run(port=5000)

# @app.route('/logout', methods=['POST'])
# def logout():
#     try:
#         # 사용자 ID 추출
#         user_info = request.get_json()
#         if not user_info or 'id' not in user_info:
#             return jsonify({'status': 'failure', 'message': 'User ID is required'}), 400

#         user_id = user_info['id']
#         print("Logging out user:", user_id)

#         # 데이터베이스 연결 및 안전한 SQL 쿼리 실행
#         with engine.begin() as connection:  # 트랜잭션이 자동으로 시작되고 커밋/롤백 관리
#             query = text("DELETE FROM tb_recommendation WHERE user_id = :user_id")
#             result = connection.execute(query, {"user_id": user_id})
#             print(f"Deleted {result.rowcount} rows for user_id {user_id}.")

#         # 성공적으로 삭제 후 응답 반환
#         print("Returning success response...")
#         return jsonify({'status': 'success', 'message': f'Data for user {user_id} deleted successfully'}), 200

#     except SQLAlchemyError as e:
#         # SQLAlchemy 관련 에러를 잡음
#         print("SQLAlchemy error occurred:", e)
#         traceback.print_exc()  # 자세한 에러 스택 출력
#         return jsonify({'status': 'failure', 'message': 'Database operation failed'}), 500

#     except Exception as e:
#         # 일반적인 예외를 잡음
#         print("Unexpected error during logout:", e)
#         traceback.print_exc()  # 자세한 에러 스택 출력
#         return jsonify({'status': 'failure', 'message': 'An unexpected error occurred'}), 500

from flask import Flask, request, jsonify
import subprocess
import json

from db_config import engine
from sqlalchemy.exc import SQLAlchemyError

app = Flask(__name__)

@app.route('/user_info', methods=['POST'])
def receive_user_info():
    user_info = request.get_json()
    print("Received user info:", user_info)
    
    # 사용자 정보를 user_profile.json 파일에 저장
    with open('user_profile.json', 'w', encoding='utf-8') as f:
        json.dump(user_info, f, ensure_ascii=False, indent=4)
    
    # model.py 실행
    try:
        subprocess.run(['python', 'model.py'], check=True)
    except subprocess.CalledProcessError as e:
        print("Error executing model.py:", e)
        return jsonify({'status': 'failure', 'message': 'Model execution failed'}), 500
    
    return jsonify({'status': 'success', 'message': 'User info received and model.py executed'}), 200
from sqlalchemy.sql import text
from sqlalchemy.exc import SQLAlchemyError
import traceback

@app.route('/logout', methods=['POST'])
def logout():
    try:
        # 사용자 ID 추출
        user_info = request.get_json()
        if not user_info or 'id' not in user_info:
            return jsonify({'status': 'failure', 'message': 'User ID is required'}), 400

        user_id = user_info['id']  # API에서 전달받은 사용자 ID
        print(f"Logging out user: '{user_id}'")

        # 데이터베이스 연결 및 안전한 SQL 쿼리 실행
        with engine.begin() as connection:  # 트랜잭션 관리
            # 데이터 존재 여부 확인
            check_query = text("SELECT COUNT(*) FROM tb_recommendation WHERE ID = :user_id")
            count_result = connection.execute(check_query, {"user_id": user_id}).scalar()
            print(f"Rows found for user_id '{user_id}': {count_result}")

            if count_result == 0:
                return jsonify({'status': 'failure', 'message': f'No data found for user_id {user_id}'}), 404

            # 데이터가 존재하면 삭제 진행
            delete_query = text("DELETE FROM tb_recommendation WHERE ID = :user_id")
            result = connection.execute(delete_query, {"user_id": user_id})
            print(f"Deleted {result.rowcount} rows for user_id {user_id}.")

        print("Returning success response...")
        return jsonify({'status': 'success', 'message': f'Data for user {user_id} deleted successfully'}), 200

    except SQLAlchemyError as e:
        # SQLAlchemy 오류 처리
        print("SQLAlchemy error occurred:", e)
        traceback.print_exc()
        return jsonify({'status': 'failure', 'message': 'Database operation failed'}), 500

    except Exception as e:
        # 기타 일반 오류 처리
        print("Unexpected error during logout:", e)
        traceback.print_exc()
        return jsonify({'status': 'failure', 'message': 'An unexpected error occurred'}), 500
    
@app.route('/update_user_data', methods=['POST'])
def update_user_data():
    try:
        user_info = request.get_json()
        if not user_info or 'id' not in user_info:
            return jsonify({'status': 'failure', 'message': 'User ID is required'}), 400

        user_id = user_info['id']
        print(f"Updating data for user: '{user_id}'")

        # Delete existing data
        with engine.begin() as connection:
            delete_query = text("DELETE FROM tb_recommendation WHERE ID = :user_id")
            result = connection.execute(delete_query, {"user_id": user_id})
            print(f"Deleted {result.rowcount} rows for user_id {user_id}.")

        user_info = request.get_json()
        print("Received user info:", user_info)
        
        # 사용자 정보를 user_profile.json 파일에 저장
        with open('user_profile.json', 'w', encoding='utf-8') as f:
            json.dump(user_info, f, ensure_ascii=False, indent=4)
        
        # model.py 실행
        try:
            subprocess.run(['python', 'model.py'], check=True)
        except subprocess.CalledProcessError as e:
            print("Error executing model.py:", e)
            return jsonify({'status': 'failure', 'message': 'Model execution failed'}), 500
    
        return jsonify({'status': 'success', 'message': 'User info received and model.py executed'}), 200
    except SQLAlchemyError as e:
        print("SQLAlchemy error occurred:", e)
        traceback.print_exc()
        return jsonify({'status': 'failure', 'message': 'Database operation failed'}), 500

    except Exception as e:
        print("Unexpected error during update_user_data:", e)
        traceback.print_exc()
        return jsonify({'status': 'failure', 'message': 'An unexpected error occurred'}), 500
    
if __name__ == '__main__':
    app.run(port=5000)