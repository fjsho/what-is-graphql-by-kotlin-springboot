# GraphQL Demo with Kotlin and Spring Boot

このプロジェクトはKotlinとSpring Bootを使用してGraphQL APIを実装した簡単なデモアプリケーションです。  
Claudを用いて作成しました。  
https://claude.ai/share/d69fc28b-ff41-4453-b420-41741860a759  
ToDoリストの基本的なCRUD操作を通じて、GraphQLの基本概念と実装方法を示しています。

## プロジェクトの目的

- GraphQLの基本的な概念と利点を示す
- KotlinとSpring Bootを使ったGraphQL APIの実装例を提供する
- ToDoリストアプリケーションを通じてCRUD操作の実装方法を解説する

## プロジェクト構成

```
graphql-demo/
├── build.gradle.kts              # プロジェクト依存関係
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   └── com/example/graphqldemo/
│   │   │       ├── GraphqlDemoApplication.kt   # アプリケーションエントリポイント
│   │   │       ├── controller/
│   │   │       │   └── TodoController.kt       # GraphQLエンドポイント
│   │   │       ├── model/
│   │   │       │   └── Todo.kt                 # データモデル
│   │   │       └── repository/
│   │   │           └── TodoRepository.kt       # データアクセス層
│   │   └── resources/
│   │       ├── application.properties          # アプリケーション設定
│   │       └── graphql/
│   │           └── schema.graphqls             # GraphQLスキーマ定義
│   └── test/                                   # テストコード
```

## 機能

- ToDoアイテムの一覧取得
- 特定のToDoアイテムの取得
- 新しいToDoアイテムの作成
- 既存のToDoアイテムの更新
- ToDoアイテムの削除

## 起動方法

1. プロジェクトをクローンまたはダウンロードする
2. プロジェクトルートディレクトリで以下のコマンドを実行する

```bash
./gradlew bootRun
```

3. アプリケーションは `http://localhost:8080` で起動します
4. GraphiQL（GraphQLクエリエディタ）に `http://localhost:8080/graphiql` からアクセスできます

## プロジェクトの詳細

### 1. プロジェクト構成
- `build.gradle.kts`: Spring BootとGraphQLの依存関係を設定
- `GraphqlDemoApplication.kt`: アプリケーションのエントリーポイント

### 2. GraphQLスキーマ設計
- `schema.graphqls`: TodoアイテムのCRUD操作を定義
  - `Query`: データ取得操作（全Todoリスト、ID指定取得）
  - `Mutation`: データ変更操作（追加、更新、削除）

### 3. モデルとリポジトリ
- `Todo.kt`: データモデルの定義
- `TodoRepository.kt`: データアクセスとCRUD操作の実装

### 4. GraphQLコントローラー
- `TodoController.kt`: GraphQLのクエリとミューテーションに対応するエンドポイント
  - `@QueryMapping`: クエリ操作のハンドラー
  - `@MutationMapping`: ミューテーション操作のハンドラー
  - `@Argument`: GraphQLクエリからの引数受け取り

### 5. アプリケーション設定
- Spring GraphiQLを有効化（ブラウザベースのGraphQLクエリツール）


## What is GraphQL 🤔

### GraphQLの主な特徴と利点
GraphQLの最大の特徴は、クライアントが必要なデータだけを正確に指定できる点です。
同じエンドポイントを使って、必要なフィールドだけを取得することができます。

1. **クライアント主導のデータ取得**：
- クライアントが必要なデータを正確に指定できる
- オーバーフェッチングやアンダーフェッチングの問題を解決

2. **単一エンドポイント**：
- 複数のリソースを1回のリクエストで取得できる
- ネットワークリクエスト数の削減

3. **強力な型システム**：
- スキーマ定義によりAPIの形状が明確になる
- クライアント側での型安全性向上

4. **バージョン管理の容易さ**：
- 新しいフィールドの追加が既存クライアントに影響しない
- APIの進化が容易

### GraphQLクエリ例
GraphQL APIに対するcurlコマンドの実行例

1. 全てのTodoを取得するクエリ
```bash
curl -X POST \
   -H "Content-Type: application/json" \
   -d '{"query": "{ todos { id title completed } }"}' \
   http://localhost:8080/graphql
```

2. 特定のTodoを取得するクエリ（ID: 1）
```bash
curl -X POST \
   -H "Content-Type: application/json" \
   -d '{"query": "{ todo(id: 1) { id title completed } }"}' \
   http://localhost:8080/graphql
```
3. 新しいTodoを追加するミューテーション
```bash
curl -X POST \
   -H "Content-Type: application/json" \
   -d '{"query": "mutation { addTodo(title: \"GraphQLについて詳しく調べる\") { id title completed } }"}' \
   http://localhost:8080/graphql
```
4. Todoを更新するミューテーション（ID: 2、タイトルと完了ステータスを更新）
```bash
curl -X POST \
   -H "Content-Type: application/json" \
   -d '{"query": "mutation { updateTodo(id: 2, title: \"KotlinでGraphQLを学ぶ\", completed: true) { id title completed } }"}' \
   http://localhost:8080/graphql
```
5. Todoを削除するミューテーション（ID: 3）
```bash
curl -X POST \
   -H "Content-Type: application/json" \
   -d '{"query": "mutation { deleteTodo(id: 3) }"}' \
   http://localhost:8080/graphql
```
6. 複数の操作を一度に実行（全てのTodoを取得しながら、新しいTodoも追加）
```bash
curl -X POST \
   -H "Content-Type: application/json" \
   -d '{"query": "{ todos { id title completed } } mutation { addTodo(title: \"curlでGraphQLを試す\") { id title } }"}' \
   http://localhost:8080/graphql
```
7. クエリに変数を使用する例
```bash
curl -X POST \
   -H "Content-Type: application/json" \
   -d '{"query": "query GetTodo($id: ID!) { todo(id: $id) { id title completed } }", "variables": {"id": 1}}' \
   http://localhost:8080/graphql
```

## 発展的な学習

このデモプロジェクトを拡張するアイデア：

1. データベース連携（Spring Data JPA + H2/PostgreSQL）
2. エラーハンドリングの強化
3. 認証・認可の実装
4. サブスクリプション機能の追加
5. クライアントアプリケーションの実装（React + Apollo Clientなど）

## 参考リソース

- [GraphQL公式ドキュメント](https://graphql.org/)
- [Spring GraphQL公式ドキュメント](https://docs.spring.io/spring-graphql/docs/current/reference/html/)


GraphQLは従来のRESTful APIの代替となるクエリ言語およびランタイムで、クライアントが必要なデータを正確に指定できるようにするものです。
