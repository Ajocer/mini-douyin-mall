-- 创建 user 表
CREATE TABLE IF NOT EXISTS user (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    email VARCHAR(255) NOT NULL UNIQUE,
    password_hashed VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- 创建 category 表
CREATE TABLE IF NOT EXISTS category (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- 创建 product 表
CREATE TABLE IF NOT EXISTS product (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- 创建 product_category 表
CREATE TABLE IF NOT EXISTS product_category (
                                                category_id INT NOT NULL,
                                                product_id INT NOT NULL,
                                                PRIMARY KEY (category_id, product_id),
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
    );

-- 创建 cart 表
CREATE TABLE IF NOT EXISTS cart (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    user_id INT NOT NULL,
                                    product_id INT NOT NULL,
                                    qty INT NOT NULL,
                                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建 Base 表结构（实际不会单独创建，字段会被继承）
CREATE TABLE IF NOT EXISTS base (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建 Order 表
CREATE TABLE IF NOT EXISTS `order` (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                       order_id VARCHAR(256) NOT NULL UNIQUE,
    user_id INT UNSIGNED NOT NULL,
    user_currency VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    street_address VARCHAR(255),
    city VARCHAR(255),
    state VARCHAR(255),
    country VARCHAR(255),
    zip_code INT,
    order_state ENUM('placed', 'paid', 'canceled') NOT NULL
    );

-- 创建 OrderItem 表
CREATE TABLE IF NOT EXISTS order_item (
                                          id INT AUTO_INCREMENT PRIMARY KEY,
                                          created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                          updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                          order_id_refer VARCHAR(256) NOT NULL,
    product_id INT,
    quantity INT,
    price DECIMAL(10, 2),
    FOREIGN KEY (order_id_refer) REFERENCES `order`(order_id) ON DELETE CASCADE
    );

-- 创建 payment 表
CREATE TABLE IF NOT EXISTS payment (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                       user_id INT UNSIGNED NOT NULL,
                                       order_id VARCHAR(256) NOT NULL,
    transaction_id VARCHAR(255) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    pay_at DATETIME NOT NULL
    );
