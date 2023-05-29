export interface JobDetails {
  job_id: number;
  job_name: string;
  brand: string;
  country: string;
  department_name: string;
  product_line: string;
  job_status: string;
  created_date: string;
  user_id: number;
  _version_: number;
}

export interface SorlResponse {
  data: JobDetails[];
  cursor: any;
}
