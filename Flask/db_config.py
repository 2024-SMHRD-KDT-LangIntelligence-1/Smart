# db_config.py

from sqlalchemy import create_engine

# 데이터베이스 연결 정보 설정
DB_USER = 'campus_24K_LI_p2_1'        # 데이터베이스 사용자명
DB_PASSWORD = 'smhrd1'  # 데이터베이스 비밀번호
DB_HOST = 'project-db-campus.smhrd.com'           # 호스트 (보통 'localhost')
DB_PORT = '3307'                # 포트 (기본적으로 MySQL은 3306 포트 사용)
DB_NAME = 'campus_24K_LI_p2_1'        # 데이터베이스 이름

# SQLAlchemy 엔진 생성
DATABASE_URI = f'mysql+pymysql://{DB_USER}:{DB_PASSWORD}@{DB_HOST}:{DB_PORT}/{DB_NAME}'
engine = create_engine(DATABASE_URI, echo=True)