CREATE TABLE BOARD_COLUMNS(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    order_column INTEGER NOT NULL,
    kind VARCHAR(50) NOT NULL,
    board_id BIGINT NOT NULL,
    CONSTRAINT fk_board__board_columns FOREIGN KEY (board_id) REFERENCES BOARDS(id) ON DELETE CASCADE,
    CONSTRAINT uk_id_order UNIQUE KEY unique_board_id__order_column (board_id, order_column)
);