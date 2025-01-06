# from concurrent.futures import ThreadPoolExecutor, as_completed
# import os
# import pandas as pd



# user_profile = {
#     'id': '',
#     'preference': []  # 사용자가 선택한 장르
# }

# def run_model(user_profile):
#     # # 필요한 추가 라이브러리 임포트
#     import pandas as pd
#     try:
#         import pandas as pd
#     #     from langchain.llms import OpenAI
#     #     from langchain.prompts import ChatPromptTemplate
#     except ImportError:
#         raise ImportError("langchain 라이브러리가 설치되어 있지 않습니다. 'pip install langchain' 명령어로 설치해주세요.")

#     # # OPENAI API 키 설정
#     # # 보안상의 이유로 API 키를 코드에 직접 포함시키는 것은 권장되지 않습니다.
#     # # 대신, 환경 변수로 설정하거나 안전한 방법으로 관리하세요.
#     # # 예시:
#     # # os.environ['OPENAI_API_KEY'] = 'your-api-key'
#     # if 'OPENAI_API_KEY' not in os.environ:
#     #     raise EnvironmentError("OPENAI_API_KEY가 환경 변수에 설정되어 있지 않습니다.")
    
#     # 파일 경로 설정
#     file_path = 'C:\\Users\\SMHRD\\Desktop\\cral\\templates\\all_book.csv'

#     # CSV 파일 읽기
#     try:
#         df = pd.read_csv(file_path, encoding='utf-8')  # 파일 인코딩 지정

#         # 'GENRE' 컬럼에서 앞 5글자 삭제
#         df['GENRE'] = df['GENRE'].str[5:]

#         # GENRE 컬럼에서 대분류, 중분류, 소분류, 세부분류 분리
#         df[['대분류', '중분류', '소분류', '세부분류']] = df['GENRE'].str.split('>', expand=True, n=3)
#     except FileNotFoundError:
#         print(f"Error: File not found at {file_path}")
#         return None
#     except Exception as e:
#         print(f"An error occurred: {e}")
#         return None

#     def recommend_books_based_on_selection(user_profile, min_count=5):
#         """
#         사용자가 회원가입 시 선택한 장르(preference)를 기반으로 도서를 추천합니다.
#         최소 `min_count` 권의 도서를 반환합니다.

#         Args:
#             user_profile (dict): 사용자의 회원 정보를 포함하는 딕셔너리.
#             min_count (int): 최소 추천 도서 개수.

#         Returns:
#             list: 추천 도서 제목 리스트.
#         """
#         # 1. 사용자가 선택한 선호 장르 가져오기
#         preference = user_profile.get('preference', [])
#         if not preference:
#             print("선호 장르가 제공되지 않았습니다. 전체 도서를 무작위로 추천합니다.")
#             return df['TITLE'].sample(n=min_count, replace=True).tolist()

#         # 2. 선택한 장르를 기준으로 데이터 필터링
#         matched_indices = []
#         for genre in preference:
#             for column in ['대분류', '중분류', '소분류', '세부분류']:
#                 indices = df[df[column].str.contains(genre, na=False, case=False)].index.tolist()
#                 matched_indices.extend(indices)

#         # 3. 중복 제거 및 데이터 정리
#         matched_indices = list(set(matched_indices))
#         matched_books = df.loc[matched_indices]

#         # 4. 결과 반환
#         if len(matched_books) >= min_count:
#             recommended_titles = matched_books['TITLE'].tolist()
#         else:
#             # 부족한 도서 개수만큼 무작위 추가
#             additional_books = df[~df.index.isin(matched_indices)]
#             additional_books = additional_books.sample(n=min_count - len(matched_books), replace=True)
#             combined_books = pd.concat([matched_books, additional_books])
#             recommended_titles = combined_books['TITLE'].tolist()

#         return recommended_titles

#     # 도서 추천 실행
#     recommended_books = recommend_books_based_on_selection(user_profile)

#     # 추천 도서 목록 생성
#     dfs1 = []
#     id = user_profile.get('id', 'User')
#     dfs1.append(f"{id} 님을 위한 추천 도서 목록:\n")
#     for i, book in enumerate(recommended_books, 1):
#         dfs1.append(f"{i}. {book}\n")

#     # 추천 도서 목록 문자열
#     df1 = ''.join(dfs1)

#     # 데이터프레임 복사
#     df2 = df.copy()

#     # df1의 책 제목과 df의 타이틀을 비교하여 같은 책을 df2에 저장
#     recommended_titles = [book.strip() for book in recommended_books]
#     df3 = df2[df2['TITLE'].isin(recommended_titles)][['TITLE', 'BOOK_DESC']]

#     # BOOK_DESC가 NaN인 경우 제외
#     df3 = df3.dropna(subset=['BOOK_DESC'])
#     print(df3)
import json
import pandas as pd
from sqlalchemy.exc import SQLAlchemyError
from db_config import engine

def run_model(user_profile):
    import pandas as pd
    from sqlalchemy.exc import SQLAlchemyError

    file_path = 'C:\\Users\\SMHRD\\Desktop\\cral\\all_book.csv'
    print(f"CSV 파일 경로: {file_path}")

    try:
        df = pd.read_csv(file_path, encoding='utf-8')
        print("CSV 파일을 성공적으로 읽었습니다.")
        df['GENRE'] = df['GENRE'].str[5:]
        df[['대분류', '중분류', '소분류', '세부분류']] = df['GENRE'].str.split('>', expand=True, n=3)
        print("GENRE 컬럼을 처리했습니다.")
    except FileNotFoundError:
        print(f"Error: File not found at {file_path}")
        return None
    except Exception as e:
        print(f"An error occurred while processing the CSV: {e}")
        return None

    def recommend_books_prioritized(user_profile, required_count=8):
        preference = user_profile.get('preference', [])
        if not preference:
            print("선호 장르가 제공되지 않았습니다. 전체 도서 중 무작위로 추천합니다.")
            return df['TITLE'].tolist()[:required_count]

        priority_columns = ['세부분류', '소분류', '중분류', '대분류']
        recommended_titles = []
        recommended_indices = set()

        for column in priority_columns:
            for genre in preference:
                # 일치하는 책을 필터링
                matched_books = df[df[column].str.contains(genre, na=False, case=False)]
                # 추천 목록에 아직 포함되지 않은 책들만 선택
                new_books = matched_books[~matched_books['TITLE'].isin(recommended_titles)]
                for title in new_books['TITLE']:
                    if len(recommended_titles) < required_count:
                        recommended_titles.append(title)
                        recommended_indices.add(new_books[new_books['TITLE'] == title].index[0])
                    else:
                        break
                if len(recommended_titles) >= required_count:
                    break
            if len(recommended_titles) >= required_count:
                break

        if len(recommended_titles) < required_count:
            print(f"관련성이 높은 도서가 {len(recommended_titles)}권 있으며, 나머지 {required_count - len(recommended_titles)}권을 추가로 추천할 수 없습니다.")
            # 필요한 경우 추가로 다른 기준으로 채울 수 있습니다.
        
        return recommended_titles

    recommended_books = recommend_books_prioritized(user_profile)
    # print(f"추천 도서 목록: {recommended_books}")

    # 사용자에게 추천 도서 목록 출력
    dfs1 = []
    user_id = user_profile.get('id', 'User')
    dfs1.append(f"{user_id} 님을 위한 추천 도서 목록:\n")
    for i, book in enumerate(recommended_books, 1):
        dfs1.append(f"{i}. {book}\n")

    df1 = ''.join(dfs1)
    # print(df1)

    # 추천된 도서의 상세 정보를 추출
    df2 = df.copy()
    recommended_titles = [book.strip() for book in recommended_books]
    try:
        df3 = df2[df2['TITLE'].isin(recommended_titles)][['TITLE', 'BOOK_DESC','BOOK_CODE','BOOK_IDX']]
        df3 = df3[['TITLE', 'BOOK_DESC','BOOK_CODE','BOOK_IDX']].head(8)
        print("df3을 생성했습니다:")
        print(df3)
    except Exception as e:
        print(f"An error occurred while creating df3: {e}")
        return None  # df3이 생성되지 않으면 함수 종료

    # 데이터베이스에 삽입
    try:
        user_id = user_profile.get('id', 'User')  # user_profile에서 user_id 추출
        if 'TITLE' not in df3.columns:
            raise ValueError("df3 does not contain 'TITLE' column")
        
        # 컬럼 매핑
        df3['ID'] = user_id
        df3['title'] = df3['TITLE']
        df3['book_title'] = df3['BOOK_DESC']
        df3['book_index'] = df3['BOOK_CODE']
        df3['book_idx'] = df3['BOOK_IDX']
        
        # 필요한 컬럼만 선택
        insert_df = df3[['ID','title','book_title','book_index','book_idx']]
        
        # 데이터베이스에 삽입
        insert_df.to_sql('tb_recommendation', con=engine, if_exists='append', index=False)
        print("Data inserted into tb_recommendation successfully.")
    except SQLAlchemyError as e:
        print("Error inserting into database:", e)
    except Exception as e:
        print("Unexpected error:", e)

def main():
    try:
        with open('user_profile.json', 'r', encoding='utf-8') as f:
            user_profile = json.load(f)
        print("User profile:")
        for key, value in user_profile.items():
            print(f"{key}: {value}")

        run_model(user_profile)

    except Exception as e:
        print("Error reading user_profile.json:", e)

if __name__ == '__main__':
    main()


#     # -------------------------------------------------------------------
#     # 추가: 감정 분석 및 상위 10개 도서 추출
# word="기쁨"
# from concurrent.futures import ThreadPoolExecutor, as_completed
# import os

# api_key = 'api키'
# os.environ['OPENAI_API_KEY'] = api_key

# from langchain_openai import ChatOpenAI
# from langchain.prompts import ChatPromptTemplate

# emotion = ["공포", "분노", "슬픔", "기쁨", "신뢰", "혐오", "기대", "놀람"]
# llm = ChatOpenAI(model_name="gpt-4o-mini", temperature=0.5, max_tokens=300)

# prompt_template = ChatPromptTemplate.from_messages([
#     ("system", f"""이 시스템은 단어 분석 전문가입니다. {{corpus}}에서 표현된 감정을
#     {emotion}의 감정의 유사도를 비교해서 출력해주세요. 유사도는 소수점 2째 자리까지 출력해주세요.
#     첫줄의 설명 응답은 출력하지 말아주세요
#     유사도 순으로 정렬해주세요
#     출력형식 : "감정 목록" 유사도 : 0.00%"""),
#     ("user", "{input}")
# ])

# chain = prompt_template | llm

# # 결과를 저장할 리스트
# titles = []

# # 병렬 처리를 위한 함수
# def analyze_emotion(row):
#     corpus = row['BOOK_DESC']
#     response = chain.invoke({"input": word, "corpus": corpus})
#     lines = response.content.split('\n')
#     highest_emotion = max(lines, key=lambda line: float(line.split(': ')[1].replace('%', '')))
#     if highest_emotion.split(' ')[0].strip('""') == word:
#         return row['TITLE']
#     return None


# # 병렬 처리 실행
# titles = []
# with ThreadPoolExecutor(max_workers=5) as executor:
#     futures = [executor.submit(analyze_emotion, row) for index, row in df3.iterrows()]
#     for future in as_completed(futures):
#         result = future.result()
#         if result:
#             titles.append(result)
#         if len(titles) >= 10:
#             break

# # df4에 제목 저장
# df4 = pd.DataFrame(titles[:10], columns=['TITLE'])
# print(df4)

#---------------------------------------------------------------------------------------------------------------------------
# import json

# def main():
#     # user_profile.json 파일에서 사용자 정보 읽기
#     try:
#         with open('user_profile.json', 'r', encoding='utf-8') as f:
#             user_profile = json.load(f)
#         print("User profile:")
#         for key, value in user_profile.items():
#             print(f"{key}: {value}")
#     except Exception as e:
#         print("Error reading user_profile.json:", e)

# if __name__ == '__main__':
#     main()

