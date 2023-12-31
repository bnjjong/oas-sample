/*
 * MIT License
 *
 * Copyright (c) 2023-present, Henry<dogfootmaster@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

CREATE TABLE member
(
    `id`           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '아이디',
    `email`        VARCHAR(100) NOT NULL COMMENT '이메일',
    `name`         VARCHAR(100) NOT NULL COMMENT '이름',
    `phone_number` VARCHAR(30)  NOT NULL COMMENT '연락처 넘버',
    `created_at`   DATETIME(6)  NULL COMMENT '생성 일자',
    PRIMARY KEY (id)
);

-- 테이블 Comment 설정 SQL - admin
ALTER TABLE member
    COMMENT '사용자 정보';

-- Unique Index 설정
CREATE UNIQUE INDEX `UIX-user-1`
    ON member (email);

CREATE UNIQUE INDEX `UIX-user-2`
    ON member (phone_number);