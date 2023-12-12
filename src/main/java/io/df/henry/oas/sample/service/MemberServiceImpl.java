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

package io.df.henry.oas.sample.service;

import io.df.henry.oas.sample.dto.MemberDto;
import io.df.henry.oas.sample.dto.MemberInsertionDto;
import io.df.henry.oas.sample.dto.MemberModificationDto;
import io.df.henry.oas.sample.model.Member;
import io.df.henry.oas.sample.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
  private final MemberRepository repository;

  @Override
  public MemberDto findById(Long memberId) {
    Member member = getMember(memberId);

    return convertMemberDto(member);
  }

  private static MemberDto convertMemberDto(Member member) {
    return new MemberDto(
        member.getId(),
        member.getEmail(),
        member.getName(),
        member.getPhoneNumber(),
        member.getCreatedAt()
    );
  }

  private Member getMember(Long memberId) {
    Member member = repository.findById(memberId).orElseThrow(
        () -> new EntityNotFoundException("member is not exists."));
    return member;
  }

  @Transactional
  @Override
  public Long save(MemberInsertionDto dto) {
    Member member = new Member(
        dto.getEmail(),
        dto.getName(),
        dto.getPhoneNumber()
    );
    return repository.save(member).getId();
  }

  @Transactional
  @Override
  public MemberDto modify(MemberModificationDto dto) {
    Member member = getMember(dto.getId());
    member.update(dto.getName(),
        dto.getPhoneNumber()
    );
    return convertMemberDto(member);
  }
}
