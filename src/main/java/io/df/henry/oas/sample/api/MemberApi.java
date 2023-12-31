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

package io.df.henry.oas.sample.api;


import io.df.henry.oas.sample.dto.MemberDto;
import io.df.henry.oas.sample.dto.MemberInsertionDto;
import io.df.henry.oas.sample.dto.MemberModificationDto;
import io.df.henry.oas.sample.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@Slf4j
@RequiredArgsConstructor
public class MemberApi {
  private final MemberService service;

  /**
   * 회원 정보를 조회 한다.
   *
   * @param memberId 멤버 ID.
   * @return {@code ResponseEntity} with member info.
   */
  @GetMapping("/{memberId}")
  public ResponseEntity<MemberDto> findById(@PathVariable("memberId") Long memberId) {
    return new ResponseEntity<>(service.findById(memberId), HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<Long> save(@RequestBody MemberInsertionDto dto) {
    Long memberId = service.save(dto);
    return new ResponseEntity<>(memberId, HttpStatus.OK);
  }

  @PutMapping("")
  public ResponseEntity<MemberDto> modify(@RequestBody MemberModificationDto dto) {
    return new ResponseEntity<>(service.modify(dto), HttpStatus.OK);
  }

  @DeleteMapping("/{memberId}")
  public ResponseEntity<Boolean> deleeteById(@PathVariable("memberId") Long memberId) {
    service.delete(memberId);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
