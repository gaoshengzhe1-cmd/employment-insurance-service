package jp.asatex.ggsz.employmentinsurance.repository.impl;

import jp.asatex.ggsz.employmentinsurance.entity.EmploymentInsuranceRate;
import jp.asatex.ggsz.employmentinsurance.repository.EmploymentInsuranceRateRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class EmploymentInsuranceRateRepositoryImpl implements EmploymentInsuranceRateRepository {

    private final DatabaseClient databaseClient;

    public EmploymentInsuranceRateRepositoryImpl(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<EmploymentInsuranceRate> findByEmploymentTypeAndActiveTrue(EmploymentInsuranceRate.EmploymentType employmentType) {
        String sql = "SELECT id, employment_type, rate, is_active, created_at, updated_at FROM employment_insurance_rates WHERE employment_type = :employmentType AND is_active = true LIMIT 1";
        
        return databaseClient.sql(sql)
                .bind("employmentType", employmentType.name())
                .map(row -> {
                    EmploymentInsuranceRate rate = new EmploymentInsuranceRate();
                    rate.setId(row.get("id", Long.class));
                    rate.setEmploymentType(EmploymentInsuranceRate.EmploymentType.valueOf(row.get("employment_type", String.class)));
                    rate.setRate(row.get("rate", java.math.BigDecimal.class));
                    rate.setIsActive(row.get("is_active", Boolean.class));
                    rate.setCreatedAt(row.get("created_at", java.time.LocalDateTime.class));
                    rate.setUpdatedAt(row.get("updated_at", java.time.LocalDateTime.class));
                    return rate;
                })
                .one();
    }
}
