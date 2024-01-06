# EnglishApp

Final Project for OOP (INT2204 2)
<hr>


## Team Members
- Vũ Văn Minh - 22021116
- Nguyễn Minh Hiển - 22021106
- Nguyễn Đăng Quân - 22021121

# Giới thiệu
- Ứng dụng từ điển giúp cho người dùng có thể tra cứu, học từ vựng tiếng Anh một cách nhanh và hiệu quả nhất.
- Kết hợp với các trò chơi giúp người học giải trí sau những giờ học căng thẳng.
- Được viết bằng ngôn ngữ Java và hỗ trợ bởi thư viện JavaFX để tạo ra giao diện đồ hoạ thú vị, đẹp mắt.

## Table of contents
- [Chức năng](#Chức-năng)
  - [Sử dụng từ điển](#Sử-dụng-từ-điển)
  - [Dịch thuật](#Dịch-thuật)
  - [Trò chơi](#Trò-chơi)
- [Công nghệ sử dụng](#Công-nghệ-sử-dụng)
  - [JavaFX](#JavaFX)
  - [Google Translate API](#Google-Translate-API)
  - [Trie](#Trie)

# Chức năng
Các tính năng mà ứng dụng sẽ hỗ trợ.
## Sử dụng từ điển
- Tra cứu từ vựng tiếng Anh: phát âm, từ loại, nghĩa, từ đồng nghĩa, trái nghĩa.
- Thêm/sửa/xóa từ vựng.
- Nghe phát âm của từ vựng.
- Thêm từ vựng vào mục yêu thích.
- Khởi tạo lại dữ liệu từ điển về ban đầu.
![](./preview/prev2.png)

## Dịch thuật
- Sử dụng công cụ dịch thuật của Google Translate để dịch từ vựng, câu văn.
- Hỗ trợ chức năng đọc đoạn văn
![](./preview/prev3.png)

## Trò chơi 
- Giúp người học giải trí
- Mang tính học thuật, hỗ trợ việc học từ vựng
![](./preview/prev4.png)
- Ba game:
  * Trắc nghiệm (Quizz)
  ![](./preview/prev5.png)
  * Tìm từ (Wordle)
  ![](./preview/prev6.png)
  * Đuổi hình bắt chữ (Catch the word)
  ![](./preview/prev7.png)


# Công nghệ sử dụng
Các kĩ thuật, công nghệ sử dụng để xây dựng ứng dụng.

## JavaFX
- Sử dụng JavaFX để tạo giao diện đồ hoạ cho ứng dụng.
## Google Translate API
- Sử dụng Google Translate API để dịch thuật.
- Ngoài ra, Google Translate API còn sử dụng để phát âm
## Trie
- Sử dụng cấu trúc dữ liệu Trie để lưu trữ từ điển.
- Trie còn được sử dụng để tìm kiếm/sửa/xóa từ vựng với tốc độ nhanh chóng.
- Trie cho kết quả chính xác 100% với độ phức tạp tuyến tính so với HashMap có khả năng bị trùng và độ phức tạp khó tính toán.
## Hỗ trợ xử lý dữ liệu dưới dạng MySQL/SQLite
## Cây kế thừa của từ điển
![](./UML.png)
Có dependencies:
![](./UMLwithDependencies.png)