package jp.asatex.ggsz.employmentinsurance.repository;

import jp.asatex.ggsz.employmentinsurance.entity.EmploymentInsuranceRate;
import reactor.core.publisher.Mono;

public interface EmploymentInsuranceRateRepository {
    Mono<EmploymentInsuranceRate> findByEmploymentTypeAndActiveTrue(EmploymentInsuranceRate.EmploymentType employmentType);
}
